package com.example.emailclient;

import com.example.emailclient.EmailCheck.EmailReceiver;
import com.example.emailclient.EmailSender.EmailSender;
import com.example.emailclient.EmailSender.SimpleEmailEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.mail.Message;


@SpringBootApplication
public class EmailClientApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EmailClientApplication.class);
        ConfigurableApplicationContext context = app.run(args);
        EmailSender emailSender = context.getBean(EmailSender.class);
        SimpleEmailEntity simpleEmailEntity = new SimpleEmailEntity(
                "lifeisg00@protonmail.com",
                "java mail",
                "mail body");
        //emailSender.sendEmail(simpleEmailEntity);
        EmailReceiver emailReceiver = context.getBean(EmailReceiver.class);

        Message[] messages = emailReceiver.getMessages("INBOX");
        emailReceiver.printMessages(messages);
        emailReceiver.logout();



    }

}
