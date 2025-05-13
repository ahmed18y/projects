
package bookingsystem ;



public class Agent extends User {
    private int agentID ;
    private String department ;
    private double commision ;
    private static int counter_agentID = 0 ;
    

    public Agent( String department, double commision, String UName, String pass, String name, String email, int cInfo) {
        super(UName, pass, name, email, cInfo);
        this.agentID = ++counter_agentID;
        this.department = department;
        this.commision = commision;
    }
     
  public void manageFlights() {
        System.out.println("Agent: " + getUserName() + " is managing flights...");
    }

    public void createBookingForCustomer(String customerName) {
        System.out.println("Agent: " + getUserName() + " is creating booking for customer: " + customerName);
    }

    public void modifyBooking(String bookingId) {
        System.out.println("Agent: " + getUserName() + " is modifying booking: " + bookingId);
    }

    public void generateReports() {
        System.out.println("Agent: " + getUserName() + " is generating reports...");
   
    }
    
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getCommision() {
        return commision;
    }

    public void setCommision(double commision) {
        this.commision = commision;
    }

    public int getAgentID() {
        return agentID;
    }
  
     
}
