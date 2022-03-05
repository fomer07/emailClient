package com.example.emailclient.EmailCheck;

import com.example.emailclient.model.FolderMessageInfo;
import com.example.emailclient.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

@Component
public class FolderIMAP implements FolderService {

    private final StoreIMAP storeIMAP;
    private Folder folder;

    @Autowired
    public FolderIMAP(StoreIMAP storeIMAP) {
        this.storeIMAP = storeIMAP;
    }

    @Override
    public void getFolderInstance(String folderName) {
        try {
            this.folder = storeIMAP.connectStore().getFolder(folderName);
            folder.open(Folder.READ_ONLY);
        } catch (MessagingException | NullPointerException e){
            e.printStackTrace();
        }
    }

    public Folder[] getFolderList(){
        try {
            return storeIMAP.connectStore().getDefaultFolder().list();
        } catch (MessagingException | NullPointerException e) {
            e.printStackTrace();
        }
        return new Folder[0];
    }

    @Override
    public void closeFolder() {
        try {
            folder.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FolderMessageInfo fetchFolderInformation() {
        try {
            String folderFullName=folder.getFullName();
            int totalMessage = folder.getMessageCount();
            int newMessage = folder.getNewMessageCount();
            int deletedMessageCount = folder.getDeletedMessageCount();
            int unreadMessageCount=folder.getUnreadMessageCount();
            boolean  hasNewMessages= folder.hasNewMessages();
            return new FolderMessageInfo(folderFullName,
                            totalMessage,newMessage,unreadMessageCount,deletedMessageCount,hasNewMessages);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message[] getMessages() {
        try {
            return folder.getMessages();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new Message[0];
    }
}
