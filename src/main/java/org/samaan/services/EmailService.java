//package org.samaan.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendRoomIdEmail(String recipientEmail, String roomId) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(recipientEmail);
//        message.setSubject("Chat Room Invitation");
//        message.setText("You have been invited to chat. Use this Room ID: " + roomId);
//
//        mailSender.send(message);
//    }
//}
//
