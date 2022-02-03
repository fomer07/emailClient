package com.example.emailclient.services;

import javax.mail.Session;

public interface SessionService {
    Session createAuthenticatedSession();
}
