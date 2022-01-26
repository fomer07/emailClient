package com.example.emailclient.EmailCheck;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:mailserver.properties")
public class EmailReceiver {

    @Value("${mail.receive.imap.imap}")
    String imap;
    @Value("${mail.receive.imap.address}")
    String address;
    @Value("${mail.receive.imap.password}")
    String password;
    @Value("${mail.receive.imap.port}")
    Integer port;


    public void getMails() {
        Properties properties = new Properties();
        properties.put("mail.imap.ssl.enable",true);
        properties.put("mail.store.protocol","imap");
        properties.put("mail.debug",true);
        Session session = Session.getInstance(properties);
        try {
            Store store = session.getStore();
            store.connect(imap,port,address,password);
            System.out.println(store.isConnected());
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            System.out.println(folder.getMessageCount());
            Message[] messages = folder.getMessages();
            for (Message m: messages
                 ) {
                System.out.println(m.getSubject());
                System.out.println(m.getContent().toString());
                System.out.println("<----------------------->END OF EMAIL<----------------------->");
            }
            folder.close(true);
            store.close();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

}
