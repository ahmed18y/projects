

package bookingsystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


abstract class User {
   private static int counterID=0;
   private  int userID ;
   private String userName ;
   private String password ;
   private String name ;
   private String email ;
   private int contactInfo ;
   
   public User( String UName , String pass , String name , String email , int cInfo ){
       setName(name) ;
       setPassword(pass)  ;
       setUserName (UName) ;
       setEmail(email);
       this.contactInfo = cInfo ;
       userID= ++counterID ;
   }
   public boolean login(String inputUserName , String inputPassword){
       return this.userName.equals(inputUserName)  && this.password .equals(inputPassword) ;
   }
   public void logOut(){
       System.out.println("user "+userName+"has logged out");
   }
   public void updateProfile(String name,String userNAME,String pass,String Email,int contactInfo){
     setName(name);
     setUserName(userNAME);
     setPassword(pass);
     setEmail(Email);
     setContactInfo(contactInfo);
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
   
   public void setUserName (String Uname){
       try{
     if(isUsernameUnique(Uname) && Uname !=null && containsDigit(Uname) && Uname.length()>2 ){
       this.userName = Uname ;
     }  else{
         System.out.println("incorrect user name input");
     }
     }catch(Exception e){System.out.println("! Invalied UserName !");}
   
   }
   private boolean containsDigit(String s) {
    for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) {
            return true;
        }
    }
       return false;
   }
   public boolean isUsernameUnique(String username) {
    try {
        BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length > 1) {
                String existingUsername = parts[1]; 
                if (existingUsername.equalsIgnoreCase(username)) {
                    reader.close();
                    return false; 
                }
            }
        }
        reader.close();
    } catch (IOException e) {
        System.out.println("Error reading users file.");
    }

    return true; 
}
    
   private void setPassword(String pass){
       try{
       if(pass.length()>5 && pass != null&&containsDigit(pass)){
           this.password = pass ;
       }else{
           System.out.println("not enough characters for password");
       }}catch(Exception e){System.out.println("! Invalied Password !");}
       
   }
   public void setEmail(String email) {
       try{
        if (email != null && email.contains("@gmail.com")) {
            this.email = email;

        }}catch(Exception e){System.out.println("! Invalied Email !");}
   }

    public int getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(int contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getUserName() {
        return userName;
    }

    
    public String ToString() {
        return   "userID=" + userID + ", userName=" + userName  + ", name=" + name + ", email=" + email + ", contactInfo=" + contactInfo ;
    }
   
 
    
}
