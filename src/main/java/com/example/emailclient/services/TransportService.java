package com.example.emailclient.services;


import javax.mail.Transport;
import java.util.Optional;

public interface TransportService {

    Optional<Transport> getTransport();




}
