package com.example.emailclient.services;

import com.example.emailclient.model.FolderMessageInfo;

import javax.mail.Message;

public interface FolderService {
    void getFolderInstance(String folderName);
    void closeFolder();
    FolderMessageInfo fetchFolderInformation();
    Message[] getMessages();
}
