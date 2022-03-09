package com.example.emailclient.EmailSender;

import com.example.emailclient.model.SimpleEmailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;


@Component
public class MimeMessageUtility {

    private final SessionSMTP sessionSMTP;
    @Value("${mail.sender.personalname}")
    String personalName;

    @Autowired
    public MimeMessageUtility(SessionSMTP sessionSMTP) {
        this.sessionSMTP = sessionSMTP;
    }

    public MimeMessage composeAlternativeMessage(SimpleEmailEntity simpleEmailEntity){
        MimeMessage message = prepareMimeMessage(simpleEmailEntity);
        Multipart multipartAlternative = createMultipartAlternative(simpleEmailEntity);
        try {
            message.setContent(multipartAlternative);
            message.saveChanges();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    public MimeMessage composeMixedWithAttachment(SimpleEmailEntity simpleEmailEntity,List<String> attachedFiles){
        MimeMessage message = prepareMimeMessage(simpleEmailEntity);
        Multipart attachmentAndAlternative = createMultipartWithAttachmentAndAlternative(simpleEmailEntity, attachedFiles);
        try {
            message.setContent(attachmentAndAlternative);
            message.saveChanges();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * structural mail body for html or text as alternative , and file attachments.
     * @param simpleEmailEntity pojo for mail message fields
     * @param attachedFilePaths file paths to attach body part
     * @return Multipart with "mixed" subtype
     */
    public Multipart createMultipartWithAttachmentAndAlternative (SimpleEmailEntity simpleEmailEntity, List<String> attachedFilePaths){

        Multipart multipart = new MimeMultipart();
        MimeBodyPart mainBodyPart = new MimeBodyPart();

        Multipart multipartAlternative = createMultipartAlternative(simpleEmailEntity);
        // attach alternative part to the main body part
        try {
                mainBodyPart.setContent(multipartAlternative);
                multipart.addBodyPart(mainBodyPart);
        } catch (MessagingException e) {
                e.printStackTrace();
        }

        if(attachedFilePaths != null && attachedFilePaths.size()>0){
            for (String filePath: attachedFilePaths){
                MimeBodyPart attachedPart = new MimeBodyPart();
                try {
                    FileDataSource fds = new FileDataSource(filePath);
                    attachedPart.setDataHandler(new DataHandler(fds));
                    attachedPart.setFileName(fds.getName());
                    multipart.addBodyPart(attachedPart);
                } catch ( MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
       return multipart;
    }

    /**
     * can be used for create text/plain or text/html mail messages. display depends on receiver's client application.
     * @param simpleEmailEntity represents mail message
     * @return MultiPart as multipart/alternative MIME type
     */
    public Multipart createMultipartAlternative(SimpleEmailEntity simpleEmailEntity){
        Multipart multipart = new MimeMultipart("alternative");
        try {
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(simpleEmailEntity.getMessageBody());

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(simpleEmailEntity.getMessageBody(),"text/html; charset=utf-8");

            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
         return  multipart;
    }

    /**
     * to create main structure of mail message. mail body will be added as multipart with appropriate method.
     * @param simpleEmailEntity mail message POJO
     * @return MimeMessage
     */
    public MimeMessage prepareMimeMessage(SimpleEmailEntity simpleEmailEntity){
        MimeMessage mimeMessage = new MimeMessage(sessionSMTP.createAuthenticatedSession());
        try {
            mimeMessage.setRecipients(Message.RecipientType.TO,InternetAddress.parse(simpleEmailEntity.getToAddresses()));
            mimeMessage.setSender(new InternetAddress(sessionSMTP.senderMailAddress));
            mimeMessage.setFrom(new InternetAddress(personalName + " <"+sessionSMTP.senderMailAddress+">"));
            if (simpleEmailEntity.getToCcAddresses() != null && !simpleEmailEntity.getToCcAddresses().trim().isEmpty()){
                mimeMessage.setRecipients(Message.RecipientType.CC,InternetAddress.parse(simpleEmailEntity.getToCcAddresses()));
            }
            mimeMessage.setSubject(simpleEmailEntity.getSubject());
            mimeMessage.saveChanges();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return mimeMessage;
    }


}
