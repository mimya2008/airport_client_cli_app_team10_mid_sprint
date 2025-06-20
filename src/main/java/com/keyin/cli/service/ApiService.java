package com.keyin.cli.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {

    private final String BASE_URL = "http://localhost:8080";  // your Spring Boot backend

    public void getAirportsByCity() {
        // Sample hardcoded city ID
        sendGet("/cities/1/airports");
    }

    public void getAircraftByPassenger() {
        sendGet("/passengers/1/aircraft");
    }

    public void getAirportsByAircraft() {
        sendGet("/aircraft/1/airports");
    }

    public void getAirportsByPassenger() {
        sendGet("/passengers/1/airports");
    }

    private void sendGet(String endpoint) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            System.out.println("GET " + url);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
