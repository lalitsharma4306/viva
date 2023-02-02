package com.vivatech.config;

import com.vivatech.service.UserService;
import com.vivatech.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    //get jwt
        //Bearer
        //validate
        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken=null;
        UserDetails userDetails = null;
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken= requestTokenHeader.substring(7);
            try {
                username = jwtUtils.extractUsername(jwtToken);
                log.info("Username : "+username);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(username!=null){
                userDetails = this.userService.loadUserByUsername(username);
            }
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else{
                log.info("Token is not validated.");
            }
        }
        filterChain.doFilter(request,response);

    }
}
