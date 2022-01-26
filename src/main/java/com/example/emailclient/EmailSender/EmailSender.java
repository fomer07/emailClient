package com.example.emailclient.EmailSender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * this class responsible for sending email
 * with supplied configuration properties
 * within "mail.sender" prefix
 */
@Configuration
@PropertySource("classpath:mailserver.properties")
public class EmailSender {


    @Value("${mail.sender.smtp}")
    String mailSmtpHost;
    @Value("${mail.sender.address}")
    String senderMailAddress;
    @Value("${mail.sender.password}")
    String senderMailPassword;
    @Value("${mail.sender.port}")
    Integer port;

    public EmailSender(){
    }

    public void sendEmail(SimpleEmailEntity simpleEmailEntity){
        Properties properties = new Properties();
        properties.put("mail.smtp.host",mailSmtpHost);
        properties.put("mail.smtp.port",port);
        Session session = Session.getInstance(properties);
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(senderMailAddress);
            mimeMessage.setRecipients(Message.RecipientType.CC,simpleEmailEntity.getToAddresses());
            mimeMessage.setSubject(simpleEmailEntity.getSubject());
            mimeMessage.setSentDate(new Date());
            mimeMessage.setText(simpleEmailEntity.getBody());
            Transport.send(mimeMessage,senderMailAddress,senderMailPassword);
            System.out.println("E-mail sent successful");
        }catch (MessagingException mex){
            mex.printStackTrace();
        }

    }


}
