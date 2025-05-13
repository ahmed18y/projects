
package bookingsystem;

public class payment {
    private static int paymentCount = 0;
    private int paymentID;
    Booking b ;
    private String bookingReference;
    private double Amount ;
    private String method;
    private String Status;
    private String transactionDate;

    public payment(String bookingReference, double Amount, String method) {
        this.paymentID = ++paymentCount;
        this.bookingReference=bookingReference;
        this.Amount = Amount;
        this.method = method;
        this.transactionDate = null;
        this.Status = "unpaid";
    }

    public static int getPaymentCount() {
        return paymentCount;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public Booking getB() {
        return b;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public double getAmount() {
        return Amount;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return Status;
    }

    public String getTransactionDate() {
        return transactionDate;
    }
    private boolean validatePaymentDetails(){
        if (Amount>0&&(method.equalsIgnoreCase("cash")||method.equalsIgnoreCase("visa") ||method.equalsIgnoreCase("insta pay"))){
         return true;
        }else{
            return false;
        }
    }
    public void proccessPayment(){
        if (validatePaymentDetails()){
            updateStatus();
            System.out.println("payment number: "+paymentID+"  succesfully done");
            
        }else{
            System.out.println("payment failed");
        }
    }
    public void updateStatus(){
        if(validatePaymentDetails()){
            this.Status = "paid";
        }else{
            System.out.println("invalid ");
        }
    }
    
    
    
    
}
