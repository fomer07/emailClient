package com.example.emailclient.EmailSender;

import com.example.emailclient.services.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Transport;

@Component
public class TransportSMTP implements TransportService {
    private final SessionSMTP sessionSMTP;
    private Transport transport;

    @Autowired
    public TransportSMTP(SessionSMTP sessionSMTP) {
        this.sessionSMTP = sessionSMTP;
    }

    public boolean connectTransport(){
        try {
            transport = sessionSMTP.createAuthenticatedSession().getTransport();
            transport.connect();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return transport.isConnected();
    }
    public boolean logoutTransport(){
        try {
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return transport.isConnected();
    }

    public Transport getTransport() {
        return transport;
    }

}
