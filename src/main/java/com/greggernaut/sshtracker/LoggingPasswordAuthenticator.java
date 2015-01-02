package com.greggernaut.sshtracker;

import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;

class LoggingPasswordAuthenticator implements PasswordAuthenticator {
    public boolean authenticate(String username, String password, ServerSession session) {
        System.out.println("Got attempt " + username + ":" + password + " to " + session.getIoSession().getLocalAddress() + " from " + session.getIoSession().getRemoteAddress());
        return false;
    }
}
