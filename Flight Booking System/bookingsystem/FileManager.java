package bookingsystem;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    public static void saveUsers(ArrayList<customer> customers, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i=0;i<customers.size();i++) {
                customer c=customers.get(i);
                writer.write(c.getUserName() + "," + c.getCustomerId() + "," + c.getAddress() + "," + c.getPreference() + "," + c.getContactInfo());
                writer.newLine();
            }
            System.out.println("Users saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving users.");
        }
    }

    public static ArrayList<customer> loadUsers(String filename) {
        ArrayList<customer> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // userName, id, address, preference, contactInfo
                String username = data[0];
                int id = Integer.parseInt(data[1]);
                String address = data[2];
                String pref = data[3];
                int contact = Integer.parseInt(data[4]);

               
                customer c = new customer(address, pref, username, "pass1234", "Unknown", "default@gmail.com", contact);
                users.add(c);
            }
            System.out.println("Users loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading users.");
        }
        return users;
    }

    public static void saveFlights(ArrayList<Flightt> flights, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i=0;i<flights.size();i++) {
                Flightt f=flights.get(i);
                writer.write(
                    f.getFlightNumber() + "," +
                    f.getAirline() + "," +
                    f.getOrigin() + "," +
                    f.getDestination() + "," +
                    f.getDepartureTime() + "," +
                    f.getArrivalTime() + "," +
                    f.getAvailableFirstClassSeats() + "," +
                    f.getAvailableBuisnessClassSeats() + "," +
                    f.getAvailableEconomyClassSeats() + "," +
                    f.getFirstClass$() + "," +
                    f.getEconomyClass$() + "," +
                    f.getBuisnessClass$()
                );
                writer.newLine();
            }
            System.out.println("Flights saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving flights.");
        }
    }

    public static ArrayList<Flightt> loadFlights(String filename) {
        ArrayList<Flightt> flights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] d = line.split(",");
                Flightt f = new Flightt(
    Integer.parseInt(d[0]),  // flightNumber
    d[1],                    // airline
    d[2],                    // origin
    d[3],                    // destination
    d[4],                    // departureTime
    d[5],                    // arrivalTime
    d[6],                    // date
    Integer.parseInt(d[7]),  // first class seats
    Integer.parseInt(d[8]),  // business seats
    Integer.parseInt(d[9]),  // economy seats
    Double.parseDouble(d[10]), // first class price
    Double.parseDouble(d[11]), // economy price
    Double.parseDouble(d[12])  // business price
);
                flights.add(f);
            }
            System.out.println("Flights loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading flights.");
        }
        return flights;
    }

    public static void saveBookings(ArrayList<Booking> bookings, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i=0;i<bookings.size();i++) {
                Booking b=bookings.get(i);
                writer.write(b.getBookingReference() + "," + b.getStatus() + "," + b.getPaymentStatus());
                writer.newLine();
            }
            System.out.println("Bookings saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving bookings.");
        }
    }

    public static ArrayList<Booking> loadBookings(String filename) {
        ArrayList<Booking> loaded = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] d = line.split(",");
                Booking b = new Booking(null, null); // مينفعش أعمل load كامل بدون ربط بالـ flight & customer
                System.out.println("Booking ref: " + d[0] + " (" + d[1] + ", " + d[2] + ")");
                loaded.add(b);
            }
        } catch (IOException e) {
            System.out.println("Error loading bookings.");
        }
        return loaded;
    }
}
