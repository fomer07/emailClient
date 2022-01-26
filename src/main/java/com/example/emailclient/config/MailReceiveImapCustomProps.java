package com.example.emailclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mail.receive.imap")
public class MailReceiveImapCustomProps {
    /**
     * mail receive imap host server
     */
    String imap;
    /**
     * mail receive imap host server port
     */
    Integer port;
    /**
     * mail account address
     */
    String address;
    /**
     * mail account password
     */
    String password;

    public String getImap() {
        return imap;
    }

    public void setImap(String imap) {
        this.imap = imap;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
