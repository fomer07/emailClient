package com.example.emailclient.EmailSender;

import com.example.emailclient.services.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Transport;
import java.util.Optional;

@Component
public class TransportProvider implements TransportService {

    private final SessionProvider sessionProvider;
    private final CustomTransportListener customTransportListener;
    private final CustomConnectionListener customConnectionListener;

    @Autowired
    public TransportProvider(SessionProvider sessionProvider,
                             CustomTransportListener customTransportListener,
                             CustomConnectionListener customConnectionListener) {
        this.sessionProvider = sessionProvider;
        this.customTransportListener = customTransportListener;
        this.customConnectionListener = customConnectionListener;
    }

    @Override
    public Optional<Transport> getTransport(){
        try {
            Transport transport = sessionProvider.createAuthenticatedSession().getTransport();
            transport.addTransportListener(customTransportListener);
            transport.addConnectionListener(customConnectionListener);
            transport.connect();
            return Optional.of(transport);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }




}
