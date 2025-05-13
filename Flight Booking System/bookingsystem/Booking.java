
package bookingsystem;

import java.util.ArrayList;


public class Booking {
    private static int booking_counter=0;
    private String bookingReference;
    private customer c;
    private Flightt f;
    private Passenger p;
    private ArrayList<Passenger> Passengers;
    private ArrayList<String> seatSelection;
    private String status;
    private String paymentStatus;

    public Booking( customer c,Flightt f) {
        this.bookingReference = "b"+(++booking_counter);
        this.status = "Pending";
        this.paymentStatus = "Unpaid";
        this.Passengers=new ArrayList<>();
        this.seatSelection=new ArrayList<>();
        this.c=c;
        this.f=f;
        
    }
    
    public void addPassengers(Passenger p, String seatClass){
        Passengers.add(p);
        System.out.println("Passenger: "+ p.getPassengerDetails()+ " Added Successfully");
        f.reserveSeat(seatClass);
        seatSelection.add(seatClass);
    }
    
    public double calculateTotalPrice(){
        double total =0;
        for (int i =0;i<seatSelection.size();i++){
            if (seatSelection.get(i).equalsIgnoreCase("economy")){total+=f.getEconomyClass$();}
            else if(seatSelection.get(i).equalsIgnoreCase("first")){total+=f.getFirstClass$();}
            else{
               total+=f.getBuisnessClass$();
            }
        }
        System.out.println("The Total Price = "+ total);
        return total;
    }
    
    public void confirmBooking() {
        if (Passengers.isEmpty()){
            System.out.println("no passengers added to confirm booking");
        }else{
        status = "Confirmed";
        paymentStatus = "Paid";
        System.out.println("Booking confirmed. Reference: " + bookingReference);
        c.AddtoArrayList(bookingReference); ////////////
    } }

    
    public void cancelBooking() {
        status = "Cancelled";
        paymentStatus = "Unpaid";
        System.out.println("Booking cancelled. Reference: " + bookingReference);
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public String getStatus() {
        return status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void generateItinerary(){
        System.out.println("________________________BOOKING INFORMATION________________________");
        System.out.println();
        System.out.println("The Booking Reference : "+getBookingReference());
        System.out.println(" User info:  "+c.ToString()+c.tostring()+"\n flight info:  "+f.ttoString());
        System.out.println(); 
        viewPassengers();
    }
    
    public void viewPassengers(){
        
        if(Passengers.isEmpty()){
            
            System.out.println("There Is No Passengers");
        
    }else{
            for(int i=0;i<Passengers.size();i++){
                System.out.print("Passengers: ");
            System.out.println("passenger"+(i+1)+" :"+Passengers.get(i).getPassengerDetails());
        }
        }
    }

    customer getCustomer() {
     return c;
    }
    public boolean isPaid(){
        if (status.equalsIgnoreCase("paid")){return true;}else{
            return false;
        }
    }
}

