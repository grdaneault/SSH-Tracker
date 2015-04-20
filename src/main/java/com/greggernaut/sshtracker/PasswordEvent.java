package com.greggernaut.sshtracker;

import com.google.common.base.Preconditions;

import java.net.InetAddress;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Data Storage Class for holding the details of a password attack
 */
public class PasswordEvent {
    private String username;
    private String password;
    private String ip;
    private String hostname;
    private String clientVersion;
    private String country;
    private String subdivision;
    private String city;
    private double longitude;
    private double latitude;

    private PasswordEvent() {}

    private PasswordEvent(String username, String password, String ip, String hostname, String clientVersion, String country, String subdivision, String city, double longitude, double latitude) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.hostname = hostname;
        this.clientVersion = clientVersion;
        this.country = country;
        this.subdivision = subdivision;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String toString() {
        return String.format("User: %10s  Pass: %20s IP: %15s Host: %30s Client: %40s\n",username, password, ip, hostname, clientVersion) +
        String.format("Location:   Country: %15s  Subdivision: %15s  City: %15s  (%f, %f)\n", country, subdivision, city, longitude, latitude);
    }

    public static class builder {
        private String username;
        private String password;
        private String ip;
        private String hostname;
        private String clientVersion;
        private String country;
        private String subdivision;
        private String city;
        private double longitude;
        private double latitude;

        public PasswordEvent build() {
            return new PasswordEvent(username, password, ip, hostname, clientVersion, country, subdivision, city, longitude, latitude);
        }

        public builder setUsername(String username) {
            this.username = checkNotNull(username);
            return this;
        }

        public builder setPassword(String password) {
            this.password = checkNotNull(password);
            return this;
        }

        public builder setIp(String ip) {
            this.ip = checkNotNull(ip);
            return this;
        }

        public builder setIp(InetAddress ip) {
            this.ip = checkNotNull(ip).getHostAddress();
            return this;
        }

        public builder setHostname(String hostname) {
            this.hostname = checkNotNull(hostname);
            return this;
        }

        public builder setClientVersion(String clientVersion) {
            this.clientVersion = checkNotNull(clientVersion);
            return this;
        }

        public builder setCountry(String country) {
            this.country = checkNotNull(country);
            return this;
        }

        public builder setSubdivision(String subdivision) {
            this.subdivision = subdivision;
            return this;
        }

        public builder setCity(String city) {
            this.city = city;
            return this;
        }

        public builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }
    }
}
