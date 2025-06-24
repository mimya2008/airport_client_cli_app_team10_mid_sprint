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
import java.util.List;

public class RESTClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;
    private String serverURL = "http://localhost:8080"; // default base URL

    public RESTClient() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    // Setter and getter for serverURL (used by CLI app)
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public String getServerURL() {
        return this.serverURL;
    }

    public List<Airport> getAllAirports() throws IOException, InterruptedException {
        String url = serverURL + "/airport";  // Adjust this path if your API differs
        String response = sendGetRequest(url);
        return objectMapper.readValue(response, new TypeReference<List<Airport>>() {});
    }


    // 1. Get all cities with their airports
    public List<City> getCitiesWithAirports() throws IOException, InterruptedException {
        String url = serverURL + "/city";
        String response = sendGetRequest(url);
        return objectMapper.readValue(response, new TypeReference<>() {});
    }

    // 2. Get all passengers with aircraft they've flown on
    public List<Passenger> getPassengersWithAircraft() throws IOException, InterruptedException {
        String url = serverURL + "/passenger";
        String response = sendGetRequest(url);
        return objectMapper.readValue(response, new TypeReference<>() {});
    }

    // 3. Get all aircraft with the airports they use
    public List<Aircraft> getAircraftWithAirports() throws IOException, InterruptedException {
        String url = serverURL + "/aircraft";
        String response = sendGetRequest(url);
        return objectMapper.readValue(response, new TypeReference<>() {});
    }

    // 4. Optional: Same as #2, but could point to a different endpoint in future
    public List<Passenger> getPassengerAirportUsage() throws IOException, InterruptedException {
        return getPassengersWithAircraft(); // If you have a custom endpoint, replace this
    }

    // Utility method to send a GET request and return the body
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
