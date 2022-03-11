package com.example.emailclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@AllArgsConstructor
@Getter
@ToString
public class SimpleEmailEntity {

    /**
     * comma separated sequence of addresses,
     * address syntax is of the form "user@host.domain" or "Personal Name <user@host.domain>"
     */
    private String toAddresses;
    private String toCcAddresses;
    private String subject;
    private String messageBody;
    private String replyTo;

    public SimpleEmailEntity(String toAddresses, String subject, String messageBody) {
        this.toAddresses = toAddresses;
        this.subject = subject;
        this.messageBody = messageBody;
    }
    public SimpleEmailEntity(String toAddresses, String subject, String messageBody, String replyTo) {
        this.toAddresses = toAddresses;
        this.subject = subject;
        this.messageBody = messageBody;
        this.replyTo = replyTo;
    }


}
