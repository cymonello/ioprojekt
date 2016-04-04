/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Booking;
//import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import database.OrdersDB;
import database.TermsDB;
import database.TicketsDB;



/**
 *
 * @author Kamil Oleszek
 */
public class Booking {
    //metoda tworzaca obiekty bilet w petli wg ilosci tego co wpisane w formularzu
    Ticket [] ticket; 
    //Film film;
    Client client;
    Seat [] seat;
    private Connection connect = null;
    private Statement statement = null;
    private int id;
    
    public Booking(){
        ticket = new Ticket[5];
        for(int i = 0; i < 5 ; ++i){
            ticket[i] = new Ticket(0);
        }
    }
    public void startBooking(int id){
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
    }
}

class Ticket{
    private String type;
    private int amount;
    private double price;
    private double sum;
    static int total_amount;
    static double total_sum;
    static String[] types = {"Normalny", "Szkolny", "Seniorski", "Studencki"} ;
    Ticket(int index){
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
}

class Film{
    String title;
    String[] date;
    String[] time;
    int nr_auditorium;
    
}

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