package com.example.emailclient.model;

public record FolderMessageInfo (String folderFullName,
                                 int totalMessageCount,
                                 int newMessageCount,
                                 int unReadMessageCount,
                                 int deletedMessageCount,
                                 boolean hasNewMessage
                                 ){}

