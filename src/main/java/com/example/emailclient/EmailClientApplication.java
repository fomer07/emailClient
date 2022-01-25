package com.example.emailclient;

import com.example.emailclient.EmailSender.EmailSender;
import com.example.emailclient.EmailSender.SimpleEmailEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
public class EmailClientApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EmailClientApplication.class);
        ConfigurableApplicationContext context = app.run(args);
        EmailSender emailSender = context.getBean(EmailSender.class);
        emailSender.createSimpleEmailEntity("support@bankam.tech",
                "java mail",
                "mail body");
        emailSender.sendEmail();



    }

}
