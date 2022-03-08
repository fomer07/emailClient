package com.example.emailclient.EmailCheck;

import com.example.emailclient.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Store;
import java.util.Optional;

@Component
public class StoreIMAP implements StoreService {

    private final SessionIMAP sessionIMAP;


    @Autowired
    public StoreIMAP(SessionIMAP sessionIMAP) {
        this.sessionIMAP = sessionIMAP;
    }


    @Override
    public Optional<Store> connectStore(){
        try {
            Store store = sessionIMAP.createAuthenticatedSession().getStore();
            store.connect();
            return Optional.of(store);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
