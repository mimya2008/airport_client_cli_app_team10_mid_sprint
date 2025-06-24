package com.keyin.domain;

import java.util.List;
import java.util.Objects;

public class Aircraft {
    private Long id;
    private String type;
    private String airlineName;
    private int numberOfPassengers;
    private List<Airport> airports;
    private List<Passenger> passengers;

    public Aircraft() {}

    public Aircraft(Long id, String type, String airlineName, int numberOfPassengers) {
        this.id = id;
        this.type = type;
        this.airlineName = airlineName;
        this.numberOfPassengers = numberOfPassengers;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

    public int getNumberOfPassengers() { return numberOfPassengers; }
    public void setNumberOfPassengers(int numberOfPassengers) { this.numberOfPassengers = numberOfPassengers; }

    public List<Airport> getAirports() { return airports; }
    public void setAirports(List<Airport> airports) { this.airports = airports; }

    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }

    @Override
    public String toString() {
        return "Aircraft{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", airlineName='" + airlineName + '\'' +
                ", numberOfPassengers=" + numberOfPassengers +
                ", airportCount=" + (airports != null ? airports.size() : 0) +
                ", passengerCount=" + (passengers != null ? passengers.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aircraft aircraft)) return false;
        return Objects.equals(id, aircraft.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
