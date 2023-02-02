package com.vivatech.serviceImpl;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class MailService {
    @Value("${from.email}")
    private String fromEmail;
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    public String sendTextEmail(String subject, String to, String cc, String body) throws IOException {
        return sendTextEmail(subject, to, cc, body, null, new ArrayList<>());
    }

    public String sendTextEmail(String subject, String to, String cc, String body, byte[] fileData, String fileName)
            throws IOException {
        return sendTextEmail(subject, to, cc, body, new ArrayList<>(), new ArrayList<>());
    }

    public String sendTextEmail(String subject, String to, String cc, String body, List<byte[]> filesData, List<String> fileNames)
            throws IOException {
        // the sender email should be the same as we used to Create a Single Sender
        // Verification
        Email from = new Email(fromEmail);
        Content content = new Content("text/html", body);
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(content);
        Personalization personalization = new Personalization();
        if(!ObjectUtils.isEmpty(to)) {
            for (String toMailId : to.split(","))
                personalization.addTo(new Email(toMailId));
        }
        if(!ObjectUtils.isEmpty(cc)) {
            for (String ccMailId : cc.split(","))
                personalization.addCc(new Email(ccMailId));
        }
        mail.addPersonalization(personalization);
        SendGrid sg = new SendGrid("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");

        if (!CollectionUtils.isEmpty(fileNames)) {
            int i =0;
            for (String fileName : fileNames) {
                Attachments attachments3 = new Attachments();
                String imageDataString = Base64.getEncoder().encodeToString(filesData.get(i));
                attachments3.setContent(imageDataString);
                attachments3.setFilename(fileName);
                attachments3.setDisposition("attachment");
                mail.addAttachments(attachments3);
                ++i;
            }

            MailSettings mailSettings = new MailSettings();
            Setting sandBoxMode = new Setting();
            sandBoxMode.setEnable(true);
            mailSettings.setSandboxMode(sandBoxMode);
        }
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            logger.info(response.getBody());
            return response.getBody();
        } catch (IOException ex) {
            throw ex;
        }
    }
}
