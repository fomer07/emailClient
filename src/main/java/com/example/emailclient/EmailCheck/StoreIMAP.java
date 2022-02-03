package com.example.emailclient.EmailCheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;

@Component
public class StoreIMAP {

    private final SessionIMAP sessionIMAP;
    private Store store;

    @Autowired
    public StoreIMAP(SessionIMAP sessionIMAP) {
        this.sessionIMAP = sessionIMAP;
    }

    public Store connectImapStore(){
        try {
            this.store = sessionIMAP.createAuthenticatedSession().getStore();
            store.connect();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return this.store;
    }

    public void logoutStore(){
        try {
            this.store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }





}
