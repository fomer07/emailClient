package com.example.emailclient.EmailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


@Configuration
@PropertySource("classpath:mailserver.properties")
public class EmailSender {

    private SimpleEmailEntity simpleEmailEntity;
    @Value("${mail.sender.smtp}")
    String mailSmtpHost;
    @Value("${mail.sender.address}")
    String senderMailAddress;
    @Value("${mail.sender.password}")
    String senderMailPassword;

    public EmailSender(){

    }

    public void createSimpleEmailEntity(String to,String subject,String body){
        this.simpleEmailEntity = new SimpleEmailEntity(to,subject,body);
    }

    public void sendEmail(){
        Properties properties = new Properties();
        properties.put("mail.smtp.host",mailSmtpHost);
        properties.put("mail.smtp.port",587);
        Session session = Session.getInstance(properties);
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(senderMailAddress);
            mimeMessage.setRecipients(Message.RecipientType.CC,simpleEmailEntity.getToAddresses());
            mimeMessage.setSubject(simpleEmailEntity.getSubject());
            mimeMessage.setSentDate(new Date());
            mimeMessage.setText(simpleEmailEntity.getBody());
            Transport.send(mimeMessage,senderMailAddress,senderMailPassword);
        }catch (MessagingException mex){
            mex.printStackTrace();
        }



    }


}
