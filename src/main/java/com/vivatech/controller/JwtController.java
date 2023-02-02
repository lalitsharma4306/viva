package com.vivatech.controller;

import com.vivatech.jwtModel.JwtRequest;
import com.vivatech.jwtModel.JwtResponse;
import com.vivatech.service.UserService;
import com.vivatech.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {
    private static final Logger log = LoggerFactory.getLogger(JwtController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/v1/api/gerateToken")
    public ResponseEntity <?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        log.info("Jwt request"+jwtRequest);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw new Exception("Bad Credentials !!");
        }
        UserDetails userDetails = this.userService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.jwtUtils.generateToken(userDetails);
        log.info("JWT "+token);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
