
package bookingsystem;

import java.util.ArrayList;

public class customer extends User {
    private static int customer_Counter=0;
    private String address;
    private ArrayList<String>bookingHistory= new ArrayList<>();
    private String preference;
    private int customerId;
//constructor 

    public customer(String UName, String pass, String name, String email, int cInfo) {
        super(UName, pass, name, email, cInfo);
        this.customerId=++customer_Counter;
    }

    public customer(String address,  String preference, String UName, String pass, String name, String email, int cInfo) {
        super(UName, pass, name, email, cInfo);
        this.address = address;
        this.preference = preference;
        this.customerId=++customer_Counter;
    }
    //end of constructors 
    
    
//setters & getters
    public  int getCustomerId() {
        return customerId;
    }
   
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    
    public void AddtoArrayList(String booking){
        bookingHistory.add(booking);
    }
    
    public void viewBookings(){
        if (bookingHistory.isEmpty()){
            System.out.println("No Flight Booked");
        }else{
        for (int i=0;i<bookingHistory.size();i++){
            System.out.print("->"+bookingHistory.get(i));
        }}
    }
    
    
    public String getPreference() {
        return preference;
    }
    public void setPreference(String preference) {
        this.preference = preference;
    }
    public void searchFlights(){
        System.out.println("Customer " + getUserName() + " is searching for flights...");
    }
public void createBooking() {
        System.out.println("Creating a booking for " + getUserName());
       
    }
    
    public void cancelBooking(String booking){
        System.out.println("Canceling booking.......");
        if (bookingHistory.contains(booking)){
            bookingHistory.remove(booking);
            System.out.println("Customer: "+getUserName() +"canceled flight:  "+booking);
        }else{
            System.out.println("Flight Not Found");
        }
        
    }

   
    
    public String tostring() {
        return  "address=" + address +  ", preference=" + preference + ", customerId=" + customerId ;
    }
  
}
