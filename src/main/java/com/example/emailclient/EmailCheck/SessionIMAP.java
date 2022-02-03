package com.example.emailclient.EmailCheck;

import com.example.emailclient.services.SessionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Component
public class SessionIMAP implements SessionService {

    @Value("${mail.receive.imap.imap}")
    String imap;
    @Value("${mail.receive.imap.address}")
    String address;
    @Value("${mail.receive.imap.password}")
    String password;
    @Value("${mail.receive.imap.port}")
    Integer port;

    public SessionIMAP() {
    }

    /**
     * create secure authenticated connection
     * @return session
     */
    @Override
    public Session createAuthenticatedSession(){
        Properties properties = new Properties();
        properties.put("mail.imap.ssl.enable",true);
        properties.put("mail.imap.host",imap);
        properties.put("mail.imap.port",port);
        properties.put("mail.store.protocol","imap");
        properties.put("mail.imap.auth",true);
        properties.put("mail.imap.timeout","20000");
        properties.put("mail.imap.connectiontimeout","20000");
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(address, password);
            }
        });
    }

}
