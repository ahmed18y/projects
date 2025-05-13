
package bookingsystem;

import java.util.ArrayList;

public class Administrator extends User {
    
    private static int admin_counter=0;
    private int adminId;
    private int security_Level;
    private ArrayList<Agent>agent;

    public Administrator( int security_Level, String UName, String pass, String name, String email, int cInfo) {
        super(UName, pass, name, email, cInfo);
        setSecurityLevel (security_Level);
        adminId=++admin_counter;
        agent=new ArrayList<>();
    }

    public int getSecurity_Level() {
        return security_Level;
    }

     public void setSecurityLevel(int security_Level) {
        try{
         if (security_Level >= 1) {
            this.security_Level = security_Level;
        } else {
            System.out.println("Invalid Number");
        }}catch(Exception e){
            System.out.println(" ! Invalid security_Level !");
        }
    }
    
    public void createUser() {
        System.out.println("Admin " + getUserName() + " is creating a new user...");
      
    }

    public void modifySystemSettings() {
        System.out.println("Admin " + getUserName() + " is modifying system settings...");
        
    }

    public void viewSystemLogs() {
        System.out.println("Admin " + getUserName() + " is viewing system logs...");
        
    }

    public void manageUserAccess() {
        System.out.println("Admin " + getUserName() + " is managing user access...");
       
    }
    
    public void addAgent(Agent a){
        if (security_Level>1){
        agent.add(a);}else{
            System.out.println("Security level not enough");
        }
    }
    public void removeAgent(Agent a){
        if (security_Level>1){
        agent.remove(a);}else{
             System.out.println("Security level not enough");
        }
    }
   
}
