package com.example.emailclient.services;

import javax.mail.Store;
import java.util.Optional;

public interface StoreService {
    Optional<Store> connectStore();

}
