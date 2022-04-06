package com.example.emailclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mail.sender")
public class MailSenderCustomProps {

    /**
     * mail sender smtp host server
     */
    String host;
    /**
     * mail sender address
     */
    String address;
    /**
     * mail sender password
     */
    String password;

    /**
     * mail host server port
     */
    Integer port;

    /**
     * mail sender's personal name
     */
    String personalName;

    /**
     * mail sender's protocol
     *
     */
    String protocol;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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
