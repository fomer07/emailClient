package com.example.emailclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mail.sender")
public class MailSenderCustomProps {

    /**
     * mail sender smtp host server
     */
    String smtp;
    /**
     * mail sender address
     */
    String address;
    /**
     * mail sender password
     */
    String password;

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
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
