package com.keyin.domain;

import java.util.List;

public class City {
    private String name;
    private List<Airport> airports;

    public String getName() { return name; }
    public void setName(String name) { this.name =  name; }
    public List<Airport> getAirports() { return airports; }
    public void setAirports(List<Airport> airports) { this.airports = airports; }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", airports=" + airports +
                '}';
    }
}
