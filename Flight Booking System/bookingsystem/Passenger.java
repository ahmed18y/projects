
package bookingsystem;


public class Passenger {
    private static int passenger_Counter=0;
    private int PassengerID;
    private String name;
    private String passportNumber;
    private String dateOfBirth;
    private String specialRequests;

    public Passenger( String name, String passportNumber, String dateOfBirth, String specialRequests) {
        this.PassengerID =++passenger_Counter;
         setName( name);
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth;
        this.specialRequests = specialRequests;
    }
    
    public void updateInfo(String name, String passportNumber, String dob, String requests) {
    setName(name) ;
    this.passportNumber = passportNumber;
    this.dateOfBirth = dob;
    this.specialRequests = requests;
}
    
    public void setName(String Name) {
    try {
        if (Name != null && Name.length() > 2) {
            this.name = Name;
        } else {
            System.out.println("Not enough number of characters");
        }
    } catch (Exception e) {
        System.out.println(" ! Invalid Name !");
    }
}

    
    public String getPassengerDetails() {
        return  "PassengerID=" + PassengerID + ", name=" + name + ", passportNumber=" + passportNumber + ", dateOfBirth=" + dateOfBirth + ", specialRequests=" + specialRequests ;
    }
    
    
}
