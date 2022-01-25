package com.example.emailclient.EmailSender;



public class SimpleEmailEntity {
    private final String toAddress;
    private final String subject;
    private final String body;


    public SimpleEmailEntity(String toAddress, String subject, String body) {
        this.toAddress = toAddress;
        this.subject = subject;
        this.body = body;

    }

    public String getToAddresses() {
        return toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
