package com.greggernaut.sshtracker;

import com.google.gson.Gson;
import com.maxmind.geoip2.model.CityResponse;
import com.neovisionaries.i18n.CountryCode;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by greg on 4/19/2015.
 */
public class DataGenerator {
    public static void main(String[] args) throws UnknownHostException {
        Random rand = new Random();

        Map<String, Integer> passwords = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            passwords.put(randomString(rand, rand.nextInt(3) + 7), 0);
        }

        List<String> passes = new ArrayList<>(passwords.keySet());

        for (int i = 0; i < 100; i++){
            String ip = String.format("%d.%d.%d.%d", rand.nextInt(240), rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
            //String ip  = String.format("129.21.%d.%d", rand.nextInt(255), rand.nextInt(255));
            CityResponse location = Geolocation.getInstance().locate(InetAddress.getByName(ip));
            String pass = passes.get(rand.nextInt(passes.size()));
            passwords.put(pass, passwords.get(pass) + 1);
            PasswordEvent.builder builder = new PasswordEvent.builder()
                    .setIp(ip)
                    .setHostname(randomString(rand, rand.nextInt(40)))
                    .setUsername(randomString(rand, rand.nextInt(5)))
                    .setPassword(pass);

            if (location != null) {
                CountryCode cc = CountryCode.getByCode(location.getCountry().getIsoCode());
                if (cc != null) {
                    builder.setCountry(cc.getAlpha3())
                            .setSubdivision(location.getMostSpecificSubdivision().getName())
                            .setCity(location.getCity().getName())
                            .setLongitude(location.getLocation().getLongitude())
                            .setLatitude(location.getLocation().getLatitude());
                }
            }


            HttpClient client = new HttpClient();
            Gson gson = new Gson();

            PasswordEvent event = builder.build();
            PostMethod method = new PostMethod("http://localhost:8888/add");
            System.out.println(gson.toJson(event));
            method.addParameter("event", gson.toJson(event));

            try {
                System.out.println(client.executeMethod(method));
                Thread.sleep(rand.nextInt(1000));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(passwords);
        }
    }

    private static final String RANDOM_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    public static String randomString(Random rng, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = RANDOM_CHARACTERS.charAt(rng.nextInt(RANDOM_CHARACTERS.length()));
        }
        return new String(text);
    }
}
