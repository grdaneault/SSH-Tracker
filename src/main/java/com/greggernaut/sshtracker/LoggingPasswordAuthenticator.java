package com.greggernaut.sshtracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maxmind.geoip2.model.CityResponse;
import com.neovisionaries.i18n.CountryCode;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;

import java.io.IOException;
import java.net.InetSocketAddress;

class LoggingPasswordAuthenticator implements PasswordAuthenticator {

    public LoggingPasswordAuthenticator() {
        client = new HttpClient();

        gson = new Gson();
    }

    private Gson gson;
    private HttpClient client;

    private int failedAttempts = 0;

    public boolean authenticate(String username, String password, ServerSession session) {

        InetSocketAddress remoteAddress = (InetSocketAddress) session.getIoSession().getRemoteAddress();
        CityResponse location = Geolocation.getInstance().locate(remoteAddress.getAddress());
        PasswordEvent.builder builder = new PasswordEvent.builder()
                .setIp(remoteAddress.getAddress())
                .setHostname(remoteAddress.getHostName())
                .setClientVersion(session.getClientVersion())
                .setUsername(username)
                .setPassword(password);

        if (location != null) {
            CountryCode cc = CountryCode.getByCode(location.getCountry().getIsoCode());
            builder.setCountry(cc.getAlpha3())
                    .setSubdivision(location.getMostSpecificSubdivision().getName())
                    .setCity(location.getCity().getName())
                    .setLongitude(location.getLocation().getLongitude())
                    .setLatitude(location.getLocation().getLatitude());
        }

        failedAttempts++;

        PasswordEvent event = builder.build();
        PostMethod method = new PostMethod("http://localhost:8888/add");
        System.out.println(gson.toJson(event));
        method.addParameter("event", gson.toJson(event));

        try {
            System.out.println(client.executeMethod(method));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(failedAttempts + ": " + event);

        return false;
    }
}
