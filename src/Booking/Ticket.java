package Booking;

/**
 *
 * @author Kamil Oleszek
 */
public class Ticket {
    private String type;
    private double price;
    static int total_amount;
    static double total_sum;
    private int ticketIndex;
    static String[] types = {"Normalny", "Szkolny", "Seniorski", "Stsudencki"} ;
    Ticket(int index){
        ticketIndex = index;
        type = types[index];
        switch(index){
            case 0: 
                price = 24.00;
                break;
            case 3:
            case 1:
                price = 17.00;
                break;
            case 2:
                price = 18.00;
                break;
        }
        ++total_amount;
        total_sum += price;
    }
    
    public double koszt(){
        return price;
    }
    
    public int getTicketIndex(){
        return ticketIndex;
    }
    
    
}
