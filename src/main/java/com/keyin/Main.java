package com.keyin;

import com.keyin.cli.service.ApiService;

import java.util.Scanner;

public class Main {

    ApiService apiService = new ApiService();
    Scanner scanner = new Scanner(System.in);

    public Main(ApiService apiService, Scanner scanner) {
        this.apiService = apiService;
        this.scanner = scanner;
    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== Airport CLI =====");
            System.out.println("1. View airports in a city");
            System.out.println("2. View aircraft flown by a passenger");
            System.out.println("3. View airports for an aircraft");
            System.out.println("4. View airports used by a passenger");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> apiService.getAirportsByCity();
                case 2 -> apiService.getAircraftByPassenger();
                case 3 -> apiService.getAirportsByAircraft();
                case 4 -> apiService.getAirportsByPassenger();
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
