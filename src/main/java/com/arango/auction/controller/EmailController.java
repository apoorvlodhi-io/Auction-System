package com.arango.auction.controller;

import com.arango.auction.model.EmailRequest;
import com.arango.auction.service.EmailService;
import com.arango.auction.service.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        // Extract the email parameters from the request
        String to = emailRequest.getTo();
        String subject = emailRequest.getSubject();
        String text = emailRequest.getText();

        // Send the email
        emailService.sendEmail(to, subject, text);

        return ResponseEntity.ok("Email sent successfully");
    }


}
