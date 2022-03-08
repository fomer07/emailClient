package com.example.emailclient.EmailSender;

import com.example.emailclient.services.SessionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Component
public class SessionSMTP  implements SessionService {
    @Value("${mail.sender.smtp}")
    String mailSmtpHost;
    @Value("${mail.sender.address}")
    String senderMailAddress;
    @Value("${mail.sender.password}")
    String senderMailPassword;
    @Value("${mail.sender.port}")
    Integer port;

    @Override
    public Session createAuthenticatedSession() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.ssl.checkserveridentity", "true");
        properties.put("mail.smtp.host",mailSmtpHost);
        properties.put("mail.smtp.port",port);
        properties.put("mail.smtp.auth","true");

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderMailAddress,senderMailPassword);
            }
        });
    }
}
