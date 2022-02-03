package com.example.emailclient.services;

import javax.mail.Store;

public interface StoreService {
    Store connectStore();
    void logoutStore();

}
