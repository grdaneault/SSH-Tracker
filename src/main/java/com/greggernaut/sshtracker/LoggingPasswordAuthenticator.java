package com.greggernaut.sshtracker;

import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;

import java.net.InetSocketAddress;

class LoggingPasswordAuthenticator implements PasswordAuthenticator {
    public boolean authenticate(String username, String password, ServerSession session) {

        InetSocketAddress remoteAddress = (InetSocketAddress) session.getIoSession().getRemoteAddress();
        String ip = remoteAddress.getAddress().getHostAddress();
        String clientVersion = session.getClientVersion();
        String hostname = remoteAddress.getHostName();

        System.out.println(String.format("User: %10s  Pass: %20s IP: %15s Host: %30s Client: %40s",username, password, ip, hostname, clientVersion));

        /*
         * Useful data:
         *   - Username
         *   - Password
         *   - IP
         *   - Current Hostname
         *   - GeoIP location
         *   - Client Version String
         */


        return false;
    }
}
