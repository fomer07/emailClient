package com.example.emailclient.EmailCheck;

import com.example.emailclient.model.FolderMessageInfo;
import com.example.emailclient.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.Optional;

@Component
public class FolderIMAP implements FolderService {

    private final StoreIMAP storeIMAP;

    @Autowired
    public FolderIMAP(StoreIMAP storeIMAP) {
        this.storeIMAP = storeIMAP;
    }

    @Override
    public Optional<Folder> getFolderInstance(String folderName) {
        try {
            if (storeIMAP.connectStore().isPresent()){
            Folder folder = storeIMAP.connectStore().get().getFolder(folderName);
            folder.open(Folder.READ_WRITE);
            return Optional.of(folder);
            }
        } catch (MessagingException | NullPointerException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Folder[] getFolderList(){
        try {
            if (storeIMAP.connectStore().isPresent()){
                return storeIMAP.connectStore().get().getDefaultFolder().list();
            }
        } catch (MessagingException | NullPointerException e) {
            e.printStackTrace();
        }
        return new Folder[0];
    }

    @Override
    public FolderMessageInfo fetchFolderInformation(String folderName) {
        try {
            Optional<Folder> folder = getFolderInstance(folderName);
            if (folder.isPresent()){
                String folderFullName=folder.get().getFullName();
                int totalMessage = folder.get().getMessageCount();
                int newMessage = folder.get().getNewMessageCount();
                int deletedMessageCount = folder.get().getDeletedMessageCount();
                int unreadMessageCount=folder.get().getUnreadMessageCount();
                boolean  hasNewMessages= folder.get().hasNewMessages();
                return new FolderMessageInfo(folderFullName,
                        totalMessage,newMessage,unreadMessageCount,deletedMessageCount,hasNewMessages);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new FolderMessageInfo(null,0,0,0,0,false);
    }

    @Override
    public Message[] getMessages(String folderName) {
        try {
            if (getFolderInstance(folderName).isPresent()){
                return getFolderInstance(folderName).get().getMessages();
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new Message[0];
    }
}
