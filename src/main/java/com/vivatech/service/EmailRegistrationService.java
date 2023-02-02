package com.vivatech.service;

import com.vivatech.dataClass.UserResponse;

import java.io.IOException;

public interface EmailRegistrationService {
   String sendEmail(UserResponse response) throws IOException;
}
