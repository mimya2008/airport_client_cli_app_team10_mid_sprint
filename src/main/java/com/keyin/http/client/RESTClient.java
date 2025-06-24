package com.keyin.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.domain.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class RESTClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;
    private String serverURL = "http://localhost:8080"; // default base URL

    public RESTClient() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public String getServerURL() {
        return this.serverURL;
    }

    public List<Airport> getAllAirports() {
        try {
            String url = serverURL + "/airport";
            String response = sendGetRequest(url);
            return objectMapper.readValue(response, new TypeReference<List<Airport>>() {});
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to fetch airports: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<City> getCitiesWithAirports() {
        try {
            String url = serverURL + "/city";
            String response = sendGetRequest(url);
            return objectMapper.readValue(response, new TypeReference<List<City>>() {});
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to fetch cities with airports: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Passenger> getPassengersWithAircraft() {
        try {
            String url = serverURL + "/passenger";
            String response = sendGetRequest(url);
            return objectMapper.readValue(response, new TypeReference<List<Passenger>>() {});
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to fetch passengers with aircraft: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Aircraft> getAircraftWithAirports() {
        try {
            String url = serverURL + "/aircraft";
            String response = sendGetRequest(url);
            return objectMapper.readValue(response, new TypeReference<List<Aircraft>>() {});
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to fetch aircraft with airports: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Passenger> getPassengerAirportUsage() {
        return getPassengersWithAircraft(); // Fallback to same method
    }

    private String sendGetRequest(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.err.println("HTTP GET Error: " + response.statusCode() + " from URL: " + url);
        }

        return response.body();
    }
}
