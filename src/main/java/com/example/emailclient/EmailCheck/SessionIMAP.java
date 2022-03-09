package com.example.emailclient.EmailCheck;

import com.example.emailclient.services.SessionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Component
@PropertySource("classpath:mailserver.properties")
public class SessionIMAP implements SessionService {

    @Value("${mail.receive.imap.imap}")
    String imap;
    @Value("${mail.receive.imap.address}")
    String address;
    @Value("${mail.receive.imap.password}")
    String password;
    @Value("${mail.receive.imap.port}")
    Integer port;

    /**
     * create secure authenticated connection
     * @return Session
     */
    @Override
    public Session createAuthenticatedSession(){
        Properties properties = new Properties();
        properties.put("mail.store.protocol","imap");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.starttls.enable","true");
        properties.put("mail.smtp.ssl.checkserveridentity", "true");
        properties.put("mail.user", address);
        properties.put("mail.imap.host",imap);
        properties.put("mail.imap.port",port);
        properties.put("mail.imap.auth","true");
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(address, password);
            }
        });
    }

}
