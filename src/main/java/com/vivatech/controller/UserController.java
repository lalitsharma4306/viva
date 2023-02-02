package com.vivatech.controller;

import com.vivatech.dataClass.UserDTO;
import com.vivatech.jwtModel.JwtRequest;
import com.vivatech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/api")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody UserDTO userDTO) throws IOException {
        UserDTO signUp = this.userService.signUp(userDTO);
        return new ResponseEntity(signUp, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody JwtRequest jwtRequest){
        UserDTO login = this.userService.login(jwtRequest);
        return new ResponseEntity(login, HttpStatus.CREATED);
    }
    @GetMapping("/verification/{userUid}")
    public ResponseEntity verifyUser(@PathVariable String userUid){
        String response = this.userService.verifyUser(userUid);
        return new ResponseEntity(response,HttpStatus.OK);
    }
}
