/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Booking;

/**
 *
 * @author Kamil Oleszek
 */
public class Ticket {
    private String type;
    private int amount;
    private double price;
    private double sum;
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
    }
    
    public int getTicketIndex(){
        return ticketIndex;
    }
    
    
}
