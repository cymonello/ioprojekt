/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Booking;
//import java.util.*;

import database.HallDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import database.OrdersDB;
import database.TermsDB;
import database.TicketsDB;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Kamil Oleszek
 */
public class Booking {
    //metoda tworzaca obiekty bilet w petli wg ilosci tego co wpisane w formularzu
    private List<Ticket> ticket = new ArrayList<Ticket>();
    private ArrayList<int[]> miejsce = new ArrayList<>();
    private Connection connect = null;
    private Statement statement = null;
    private int termId;
    private String[] informacje;
    
    public int listLength(){
        return ticket.size();
    }
    
    public String[] getInfo(){
        return informacje;
    }
    
    public void newTicket(int index){
        ticket.add(new Ticket(index));
    }
    
    public double price(){
        double cena = 0;
        for(int i = 0; i < ticket.size(); ++i)
            cena += ticket.get(i).koszt();
        return cena;
    }
    
    public double[] ceny(){
        double[] ceny = new double[4];
        ceny[0] = 24.05;
        ceny[1] = 17.00;
        ceny[2] = 18.00;
        ceny[3] = 17.00;
        
        return ceny;
    }
    
    public Booking(){}
    public void startBooking(int id){
        TermsDB term = new TermsDB();
        term.open();
        informacje = term.getTermInfo(id);
        this.termId = id;
        term.close();
        
        
        /*
        OrdersDB odb = new OrdersDB();
        odb.open();
        odb.addOrder(id, "Kamil", "Oleszek", "kamil@wp.pl", 798567043, "strstr");
        int _id = odb.getId();
        odb.close();
        
        TicketsDB tdb = new TicketsDB();
        tdb.open();
        for(int i = 0 ; i < 5; ++i){
            tdb.addTicket(_id, "06.04.16", "19:30", 6, 8, 7, 0);
        }
        tdb.close();
        */
        
    }
    
    public void getSeat(ArrayList<int[]> msc){
        miejsce = msc;
    }
    
    public void endBooking(String imie, String nazwisko, String email, int nr_tel){
        OrdersDB odb = new OrdersDB();
        odb.open();
        odb.addOrder(termId, imie, nazwisko, email, nr_tel, "haslomaslo");
        int orderId = odb.getId();
        odb.close();
        
        TicketsDB tdb = new TicketsDB();
        tdb.open();
        for(int i = 0; i < ticket.size(); ++i){
            tdb.addTicket(orderId, informacje[1], informacje[2], Integer.parseInt(informacje[3]), miejsce.get(i)[0], miejsce.get(i)[1] , ticket.get(i).getTicketIndex() );
        }
        tdb.close();
    }
}


class Film{
    String title;
    String[] date;
    String[] time;
    int nr_auditorium;
    
}

/*
class Client{
    String name;
    String surname;
    String mail;
    String t_number;
    static boolean mailCheck(String mail){
        return true;
    }
    static boolean telephoneCheck(String t_number){
        return true;
    }
    Client(String imie, String nazwisko, String email, String t_numer){
        name = imie;
        surname = nazwisko;
        if(mailCheck(email))
            this.mail = email;
        else
            System.out.println("Nieprawidlowy adres e-mail.");
        if(telephoneCheck(t_numer))
            this.t_number = t_numer;
        else
            System.out.println("Nieprawidlowy numer telefonu.");
    }
}

class Seat{
    public final int ROW = 10;
    public final int COLUMN = 20;
    private int row;
    private int column;
    static enum Place {szary, zielony, zolty }
     Place[][] seats = new Place[ROW][COLUMN];
    
    Seat(int r, int c){
        for(int i = 0; i < ROW; ++i){
            for(int j = 0; j < COLUMN; ++j){
                seats[i][j] = Place.szary;
            }
        }
        if(seats[r][c] == Place.zielony){
            row = r;
            column = c;
            seats[r][c] = Place.zolty;
        }
        else{
            System.out.println("To miejsce jest zajete, sprobuj wybrac inne.");
        }
    
    }

}
*/