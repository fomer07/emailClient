package com.example.emailclient.EmailSender;

import com.example.emailclient.services.SessionService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Component
@PropertySource("classpath:mailserver.properties")
@Getter
public class SessionProvider implements SessionService {

    @Value("${mail.sender.host}")
    private String mailSmtpHost;
    @Value("${mail.sender.address}")
    private String senderMailAddress;
    @Value("${mail.sender.password}")
    private String senderMailPassword;
    @Value("${mail.sender.port}")
    private Integer port;
    @Value("${mail.sender.protocol")
    private String protocol;

    @Override
    public Session createAuthenticatedSession() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol",protocol);
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.starttls.required", "true");
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
