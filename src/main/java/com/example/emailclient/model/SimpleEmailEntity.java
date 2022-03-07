package com.example.emailclient.model;

import java.util.List;

public class SimpleEmailEntity {

    /**
     * comma separated sequence of addresses,
     * address syntax is of the form "user@host.domain" or "Personal Name <user@host.domain>"
     */
    private String toAddresses;

    private String toCcAddresses;

    private String subject;

    private String messageBody;

    public SimpleEmailEntity(String toAddresses, String subject, String messageBody) {
        this.toAddresses = toAddresses;
        this.subject = subject;
        this.messageBody = messageBody;
    }

    public SimpleEmailEntity(String toAddresses, String toCcAddresses, String subject, String messageBody) {
        this.toAddresses = toAddresses;
        this.toCcAddresses = toCcAddresses;
        this.subject = subject;
        this.messageBody = messageBody;
    }

    public String getToAddresses() {
        return toAddresses;
    }

    public String getToCcAddresses() {
        return toCcAddresses;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessageBody() {
        return messageBody;
    }

    @Override
    public String toString() {
        return "SimpleEmailEntity{" +
                "toAddresses='" + toAddresses + '\'' +
                ", toCcAddresses='" + toCcAddresses + '\'' +
                ", subject='" + subject + '\'' +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }

}
