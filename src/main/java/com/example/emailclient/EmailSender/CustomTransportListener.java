package com.example.emailclient.EmailSender;


import org.springframework.stereotype.Component;

import javax.mail.Address;
import javax.mail.event.TransportAdapter;
import javax.mail.event.TransportEvent;
import java.util.logging.Logger;

@Component
public class CustomTransportListener extends TransportAdapter {

    private static final Logger LOGGER = Logger.getLogger(CustomTransportListener.class.getName());

    @Override
    public void messageDelivered(TransportEvent e) {
        LOGGER.info("Message has been successfully delivered to SMTP server for all recipients..");
    }

    @Override
    public void messageNotDelivered(TransportEvent e) {
        LOGGER.info("Message was not sent to SMTP server..");
    }

    @Override
    public void messagePartiallyDelivered(TransportEvent e) {
        StringBuilder sentAddress= new StringBuilder();
       if (e.getValidSentAddresses()!=null){
           for (Address address:
              e.getValidSentAddresses()  ) {
               sentAddress.append(address.toString()).append(", ");
           }
       }
       LOGGER.info("Message delivered to SMTP server for "+sentAddress+ " recipient addresses..");
    }
}
