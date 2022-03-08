package com.example.emailclient.services;

import com.example.emailclient.model.FolderMessageInfo;

import javax.mail.Folder;
import javax.mail.Message;
import java.util.Optional;

public interface FolderService {
    Optional<Folder> getFolderInstance(String folderName);
    FolderMessageInfo fetchFolderInformation(String folderName);
    Message[] getMessages(String folderName);
    Folder[] getFolderList();
}
