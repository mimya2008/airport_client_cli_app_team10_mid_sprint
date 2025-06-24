package com.keyin.domain;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToMany(mappedBy = "airports")
    private Set<Aircraft> aircraft;

    public Airport() {}

    public Airport(Long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }

    public Set<Aircraft> getAircraft() { return aircraft; }
    public void setAircraft(Set<Aircraft> aircraft) { this.aircraft = aircraft; }

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", city=" + (city != null ? city.getName() : "N/A") +
                ", aircraftCount=" + (aircraft != null ? aircraft.size() : 0) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport airport)) return false;
        return Objects.equals(id, airport.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
