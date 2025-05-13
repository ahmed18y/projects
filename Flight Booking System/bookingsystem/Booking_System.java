package bookingsystem;

import java.util.ArrayList;

public class Booking_System {
    private ArrayList<customer> Customer;
    private ArrayList<Flightt> flight;
    public ArrayList<Agent> agent; 
    public ArrayList<Administrator> admin; 
    private ArrayList<payment> Payment;
    private ArrayList<Booking> bookings;

    public Booking_System() {
        Customer = new ArrayList<>();
        agent = new ArrayList<>();
        admin = new ArrayList<>();
        flight = new ArrayList<>();
        bookings = new ArrayList<>();
        Payment = new ArrayList<>();
    }

    public void addCust(customer c) {
        Customer.add(c);
    }
    public void addFlight(Flightt t) {
        flight.add(t);
    }
    public ArrayList<customer> getCustomers() {
        return Customer;
    }
    public ArrayList<Flightt> getFlights() {
        return flight;
    }
    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public Booking createBooking(customer c, Flightt f, ArrayList<Passenger> passenger, ArrayList<String> seatClasses) {
        c.createBooking();
        Booking booking = new Booking(c, f);
        for (int i = 0; i < passenger.size(); i++) {
            booking.addPassengers(passenger.get(i), seatClasses.get(i));
        }
        booking.confirmBooking();
        bookings.add(booking);
        return booking;
    }

    public void processPayment(Booking b, String method) {
        payment pa = new payment(b.getBookingReference(), b.calculateTotalPrice(), method);
        pa.proccessPayment();
        Payment.add(pa);
    }

    public void generateTicket(Booking b) {
        b.generateItinerary();
    }

    public void SearchFlight(String origin, String destination, String date) {
        ArrayList<Flightt> available_flights = new ArrayList<>();
        for (int i = 0; i < flight.size(); i++) {
            Flightt f = flight.get(i);
            if (f.getOrigin().equalsIgnoreCase(origin) && f.getDestination().equalsIgnoreCase(destination) && f.getDate().equalsIgnoreCase(date)) {
                available_flights.add(f);
            }
        }
        if (available_flights.isEmpty()) {
            System.out.println("No flights found from " + origin + " to " + destination + " in " + date);
        } else {
            System.out.println("available flights: ");
            for (int i = 0; i < available_flights.size(); i++) {
                Flightt f = available_flights.get(i);
                System.out.println((i + 1) + "-" + f.ttoString());
            }
        }
    }
}