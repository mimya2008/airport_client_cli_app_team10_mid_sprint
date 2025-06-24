package com.keyin;

import com.keyin.domain.*;
import com.keyin.http.client.RESTClient;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RESTClient client = new RESTClient();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Airport CLI =====");
            System.out.println("1. View airports in each city");
            System.out.println("2. View aircraft flown by each passenger");
            System.out.println("3. View airports used by each aircraft");
            System.out.println("4. View airports used by each passenger");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1 -> {
                        List<City> cities = client.getCitiesWithAirports();
                        for (City city : cities) {
                            System.out.println("\nCity: " + city.getName());
                            if (city.getAirports() != null) {
                                city.getAirports().forEach(airport ->
                                        System.out.println("  - " + airport.getName() + " (" + airport.getCode() + ")")
                                );
                            }
                        }
                    }
                    case 2 -> {
                        List<Passenger> passengers = client.getPassengersWithAircraft();
                        for (Passenger p : passengers) {
                            System.out.println("\nPassenger: " + p.getFirstName() + " " + p.getLastName());
                            if (p.getAircraftFlown() != null) {
                                p.getAircraftFlown().forEach(a ->
                                        System.out.println("  - " + a.getType() + " (" + a.getAirlineName() + ")")
                                );
                            }
                        }
                    }
                    case 3 -> {
                        List<Aircraft> aircraftList = client.getAircraftWithAirports();
                        for (Aircraft a : aircraftList) {
                            System.out.println("\nAircraft: " + a.getType() + " by " + a.getAirlineName());
                            if (a.getAirports() != null) {
                                a.getAirports().forEach(ap ->
                                        System.out.println("  - Airport: " + ap.getName() + " (" + ap.getCode() + ")")
                                );
                            }
                        }
                    }
                    case 4 -> {
                        List<Passenger> passengers = client.getPassengerAirportUsage();
                        for (Passenger p : passengers) {
                            System.out.println("\nPassenger: " + p.getFirstName() + " " + p.getLastName());
                            if (p.getAircraftFlown() != null) {
                                p.getAircraftFlown().forEach(ac -> {
                                    System.out.println("  - Aircraft: " + ac.getType());
                                    if (ac.getAirports() != null) {
                                        ac.getAirports().forEach(airport ->
                                                System.out.println("      * " + airport.getName() + " (" + airport.getCode() + ")")
                                        );
                                    }
                                });
                            }
                        }
                    }
                    case 0 -> {
                        System.out.println("Exiting.");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
