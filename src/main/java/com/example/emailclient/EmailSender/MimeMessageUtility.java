package com.example.emailclient.EmailSender;

import com.example.emailclient.model.SimpleEmailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Component
public class MimeMessageUtility {

    private final SessionSMTP sessionSMTP;

    @Autowired
    public MimeMessageUtility(SessionSMTP sessionSMTP) {
        this.sessionSMTP = sessionSMTP;
    }

    /**
     * can be used for create text/plain or text/html mail messages. display depends on receiver's client application.
     * @param simpleEmailEntity represents mail message
     * @return MimeMessage as multipart/alternative MIME type
     */
    public MimeMessage createMultipartAlternative(SimpleEmailEntity simpleEmailEntity){
        MimeMessage mimeMessage = new MimeMessage(sessionSMTP.createAuthenticatedSession());
        try {
            mimeMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(simpleEmailEntity.getToAddresses()));

            if (simpleEmailEntity.getToCcAddresses() != null && !simpleEmailEntity.getToCcAddresses().trim().isEmpty()){
                mimeMessage.setRecipients(Message.RecipientType.CC,InternetAddress.parse(simpleEmailEntity.getToCcAddresses()));
            }
            mimeMessage.setSubject(simpleEmailEntity.getSubject());

            Multipart multipart = new MimeMultipart("alternative");

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(simpleEmailEntity.getMessageBody());

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(simpleEmailEntity.getMessageBody(),"text/html");

            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);
            mimeMessage.setContent(multipart);
            mimeMessage.saveChanges();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    return mimeMessage;
    }
}
