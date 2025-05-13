
package bookingsystem;


public class Flightt {
    private int flightNumber ;
    private String airline ;
    private String origin ;
    private String destination ;
    private String departureTime ;
    private String arrivalTime ;
    private int availableFirstClassSeats,availableBuisnessClassSeats,availableEconomyClassSeats;
    private double firstClass$,economyClass$,buisnessClass$ ;
    private String date;

    public Flightt(int flightNumber, String airline, String origin, String destination, String departureTime, String arrivalTime,String date, int availableFirstClassSeats, int availableBuisnessClassSeats, int availableEconomyClassSeats, double firstClass$, double economyClass$, double buisnessClass$) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableFirstClassSeats = availableFirstClassSeats;
        this.availableBuisnessClassSeats = availableBuisnessClassSeats;
        this.availableEconomyClassSeats = availableEconomyClassSeats;
        this.firstClass$ = firstClass$;
        this.economyClass$ = economyClass$;
        this.buisnessClass$ = buisnessClass$;
        this.date=date;
    }

   
    
  
   public boolean checkAvailability(){
       System.out.println("number of available economy seats: "+availableEconomyClassSeats);
       System.out.println("number of available first Class seats: "+availableFirstClassSeats);
       System.out.println("number of available buisness seats: "+availableBuisnessClassSeats);
       
       boolean available = (availableEconomyClassSeats + availableBuisnessClassSeats + availableFirstClassSeats) > 0;
       System.out.println("Availability: "+available);
       return available ;    
    
 }
   public void updateSchedule(String depTime,String arrivTime,String date){
    this.departureTime = depTime ;
    this.arrivalTime = arrivTime;
    this.date=date;
       System.out.println(" for flight number: "+flightNumber+"\n new departure time: "+departureTime + "\n new arrival time: "+arrivalTime+"\n date: "+ date);
       
   }
   public void calculatePrice(String seat){
       System.out.println("for the flight number :"+flightNumber);
       if (seat.equalsIgnoreCase("economy")){
           System.out.println("the price of economy class seat: "+economyClass$+" $");
           
       }
       else if(seat.equalsIgnoreCase("first class")){
           System.out.println("the price of first class seat :"+firstClass$+" $"); 
   }
       else if(seat.equalsIgnoreCase("buisness")){
           System.out.println("the price of buisness class seat :"+buisnessClass$ +" $" );
       }else{
           System.out.println("invalid seat class");
       }
       
   }
   public void reserveSeat(String seatR){
      if(checkAvailability()){
       if (seatR.equalsIgnoreCase("economy")){
           System.out.println();
           System.out.println("available Economy Class Seats Now "+ (--availableEconomyClassSeats)); 
       }
       else if(seatR.equalsIgnoreCase("first class")){
           System.out.println();
        System.out.println("available First Class Seats Now"+(--availableFirstClassSeats));
   }
       else if(seatR.equalsIgnoreCase("buisness")){
           System.out.println();
           System.out.println("available Buisness Class Seats Now "+(--availableBuisnessClassSeats) );  
       }else{
           System.out.println();
           System.out.println("invalid seat class");
       } 
      }else{
          System.out.println();
          System.out.println("No Seats Available ! ");
}

}

    public int getFlightNumber() {
        return flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getAvailableFirstClassSeats() {
        return availableFirstClassSeats;
    }

    public int getAvailableBuisnessClassSeats() {
        return availableBuisnessClassSeats;
    }

    public int getAvailableEconomyClassSeats() {
        return availableEconomyClassSeats;
    }

    public double getFirstClass$() {
        return firstClass$;
    }

    public double getEconomyClass$() {
        return economyClass$;
    }

    public double getBuisnessClass$() {
        return buisnessClass$;
    }

    public String getDate() {
        return date;
    }

    
    public String ttoString() {
        return "Flightt{" + "flightNumber=" + flightNumber + ", airline=" + airline + ", origin=" + origin + ", destination=" + destination + ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + ", availableFirstClassSeats=" + availableFirstClassSeats + ", availableBuisnessClassSeats=" + availableBuisnessClassSeats + ", availableEconomyClassSeats=" + availableEconomyClassSeats + ", firstClass$=" + firstClass$ + ", economyClass$=" + economyClass$ + ", buisnessClass$=" + buisnessClass$ + ", date=" + date + '}';
    }

  
    
   
}