package com.eticket.asoft.event.services;

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
//    public void sendBookingConfirmation(String toEmail, String eventTitle) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject("Event Ticket Confirmation");
//        message.setText("Thank you for booking. Your ticket for event '" + eventTitle + "' has been confirmed.");
//        mailSender.send(message);
//    }
//}

import com.eticket.asoft.event.model.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendBookingConfirmation(String toEmail, String eventTitle,Long id) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Event Ticket Confirmation");
            helper.setText("Thank you for booking. Your ticket for event '" + eventTitle + "' has been confirmed. Please find the attached PDF ticket with booking id::"+id);

            // Create PDF
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("\uD83C\uDFAB Event Ticket"));
            document.add(new Paragraph("Event: " + eventTitle));
            document.add(new Paragraph("Email: " + toEmail));
            document.close();

            InputStreamSource attachment = new ByteArrayResource(out.toByteArray());
            helper.addAttachment("Ticket.pdf", attachment);

            mailSender.send(message);
        } catch (MessagingException | DocumentException e) {
            e.printStackTrace();
        }
    }
    
    public void sendCancellationEmail(String email, String eventName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Your Ticket Has Been Cancelled");
            helper.setText("Dear user,\n\nYour ticket for the event '" + eventName + "' has been successfully cancelled.\n\nRegards,\nEvent Management Team");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send cancellation email", e);
        }
    }
}
