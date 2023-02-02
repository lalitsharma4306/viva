package com.vivatech.serviceImpl;

import com.vivatech.dataClass.UserResponse;
import com.vivatech.service.EmailRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

@Service
@EnableWebMvc
public class EmailRegistrationServiceImpl implements EmailRegistrationService {
    @Autowired
    private MailService mailService;
    @Override
    public String sendEmail(UserResponse response) throws IOException {
        String subject = "Get2You Varification";
        String email = response.getEmail();
        String  url = "http://localhost:9078/get2get/user/verification/"+response.getUid();
        String body =  "<div style='border:2px solid black; '> <p style='padding-left:3%; '>Hello,</p>   <p style='padding-left:3%; '> Please click on the link below to provide screening information for your RallyPay account <b>"+"</b>  </p>   <div  style=' margin-left:3%; '>    <a href='"+url+"'  style=' text-decoration:none; background-color:#28669F;  color:white; padding-top:1%;  padding-left:2%;  padding-right:2%; padding-bottom:1% ' >COMPLETE SCREENING INFO</a>   </div>   <p style='padding-left:3%; '>Thank you</p>  <p style='padding-left:3%; '>RallyPay Team</p>   </div>";
                mailService.sendTextEmail(subject,email,"",body);
        return "Mail is Send!!";
    }
}
