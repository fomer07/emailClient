package com.example.emailclient.EmailCheck;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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

    private Store store;
    private Session session;
    private Folder folder;



    /**
     * create secure authenticated connection
     * @return session
     */
    private Session createAuthenticatedSession(){
        Properties properties = new Properties();
        properties.put("mail.imap.ssl.enable",true);
        properties.put("mail.imap.host",imap);
        properties.put("mail.imap.port",port);
        properties.put("mail.store.protocol","imap");
        properties.put("mail.imap.auth",true);
        properties.put("mail.imap.timeout","20000");
        properties.put("mail.imap.connectiontimeout","20000");
        this.session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(address,password);
            }
        });
        return this.session;
    }
    private Store connectImapStore(){
        try {
            this.store = this.session.getStore();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return this.store;
    }


    /**
     *
     * @param mbox mail box folder name
     * @return all messages as array which belongs to given folder
     */
    public Message[] getMessages(String mbox) {
        Message[] messages = null;
        createAuthenticatedSession();
        connectImapStore();
        try {
            store.connect();
            System.out.println("is store connected: "+store.isConnected());
            if (mbox.equals("default")){
                folder = store.getDefaultFolder();
            }else{
                folder = store.getFolder(mbox);
            }
            folder.open(Folder.READ_ONLY);
            int totalMessageCount= folder.getMessageCount();
            System.out.println("total messages in "+folder.getFullName()+" "+totalMessageCount);
            int newMessageCount = folder.getNewMessageCount();
            System.out.println("new messages in "+folder.getFullName()+" "+newMessageCount);
            int unreadMessageCount = folder.getUnreadMessageCount();
            System.out.println("unread messages in "+folder.getFullName()+" "+unreadMessageCount);
            messages = folder.getMessages();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public void printMessages(Message[] messages){
        for (Message m:
            messages ) {
            try {
                System.out.println("Subject : "+m.getSubject());
                System.out.println("From : " + Arrays.toString(m.getFrom()));
                System.out.println("Date : " + m.getReceivedDate());
                System.out.println("Content-type : " + m.getContentType());
                Object content = m.getContent();
                if (content instanceof String){
                    System.out.println("Content as text: "+content);
                }else {
                    // TODO: 30/01/2022 handle multipart message body
                }
                System.out.println("\n");
            } catch (MessagingException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getContentText(InputStream inputStream){
        String text = null;
        try {
             text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public void logout(){
        try {
            folder.close();
            store.close();
            store = null;
            session = null;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



}
