package com.vivatech.service;

import com.vivatech.dataClass.UserDTO;
import com.vivatech.jwtModel.JwtRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

public interface UserService extends UserDetailsService {
    UserDTO signUp(UserDTO userDTO) throws IOException;
    UserDTO login(JwtRequest jwtRequest);
    String verifyUser(String userUid);
}
