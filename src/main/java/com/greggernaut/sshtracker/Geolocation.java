package com.greggernaut.sshtracker;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Helper class to handle the geoip lookups
 */
public class Geolocation {

    /**
     * The singleton instance
     */
    private static Geolocation instance = new Geolocation();

    /**
     * The configured database reader for executing the lookups
     */
    private DatabaseReader reader;

    /**
     * @return the Geoip instance
     */
    public static Geolocation getInstance() {
        return instance;
    }

    /**
     * Load the geoip database
     */
    private Geolocation() {
        try {
            File database = new File("GeoLite2-City.mmdb");
            reader = new DatabaseReader.Builder(database).build();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Geolocate an IP address
     *
     * @param address  the address to locate
     * @return  The location response, or null if it could not be located.
     */
    public CityResponse locate(InetAddress address) {
        if (reader == null) return null;

        try {
            return reader.city(address);
        } catch (AddressNotFoundException ex) {
            System.err.println(String.format("Address %s is not in the database", address));
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
