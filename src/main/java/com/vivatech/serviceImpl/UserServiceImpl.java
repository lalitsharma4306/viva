package com.vivatech.serviceImpl;

import com.vivatech.dataClass.UserDTO;
import com.vivatech.dataClass.UserResponse;
import com.vivatech.jwtModel.JwtRequest;
import com.vivatech.model.Users;
import com.vivatech.repository.UserRepo;
import com.vivatech.service.EmailRegistrationService;
import com.vivatech.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailRegistrationService emailRegistrationService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = this.userRepo.findByEmail(username);
        if(user!=null){
            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),new ArrayList<>());
        }
        else{
            throw new UsernameNotFoundException("User not found with this username");
        }

    }

    @Override
    public UserDTO signUp(UserDTO userDTO) throws IOException {
        String password = userDTO.getPassword();
        String encodePassword = this.passwordEncoder.encode(password);
        userDTO.setPassword(encodePassword);
        Users user = this.modelMapper.map(userDTO, Users.class);
        user=this.userRepo.save(user);
        userDTO=this.modelMapper.map(user,UserDTO.class);
        UserResponse userResponse = this.modelMapper.map(userDTO, UserResponse.class);
        String s = this.emailRegistrationService.sendEmail(userResponse);
        System.out.println(s);
        return userDTO;
    }

    @Override
    public UserDTO login(JwtRequest jwtRequest) {
        Users user = this.userRepo.findByEmail(jwtRequest.getEmail());
        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
    @Override
    public String verifyUser(String userUid) {
        Users users = this.userRepo.findByUid(userUid);
        if(users!=null){
            if(!users.isActive()) {
                users.setActive(true);
                Users save = this.userRepo.save(users);
                UserResponse response = this.modelMapper.map(save, UserResponse.class);
                return "User verification successfull !!";
            }
            else {
                return "User already verified !!";
            }
        }
        UserResponse userResponse = this.modelMapper.map(users, UserResponse.class);
        return "User not verified !!";
    }
}
