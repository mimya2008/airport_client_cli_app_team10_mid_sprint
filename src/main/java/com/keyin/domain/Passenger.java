package com.keyin.domain;

import java.util.List;


public class Passenger {
    private String id;
    private String name;
    private List<Aircraft> aircraftsFlown;


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Aircraft> getAircraftsFlown() { return aircraftsFlown; }
    public void setAircraftsFlown(List<Aircraft> aircraftsFlown) { this.aircraftsFlown = aircraftsFlown; }

    @Override
    public String toString() {
        return "Passenger{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", aircraftsFlown=" + aircraftsFlown +
                '}';
    }
}
