/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Booking;
//import java.util.*;

import Email.Mail;
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
import java.util.Random;



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
        if(ticket.size() >= 5){
            for(int i = 0; i < ticket.size(); ++i)
                cena += 0.7 * ticket.get(i).koszt();
        }
        else{
            for(int i = 0; i < ticket.size(); ++i)
                cena += ticket.get(i).koszt();
        }
        return cena;
    }
    
    public double[] ceny(){
        double[] ceny = new double[4];
        ceny[0] = 24.00;
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
    
    public void endBooking(String imie, String nazwisko, String email, int nr_tel, String ilosc, String cena){
        Random rand1 = new Random();
        Integer pass = rand1.nextInt(899999) + 100000;
        OrdersDB odb = new OrdersDB();
        odb.open();
        
        odb.addOrder(termId, imie, nazwisko, email, nr_tel, pass.toString());
        int orderId = odb.getId();
        odb.close();
        
        //Wysłanie maila z potwierdzeniem
        Integer tel_temp = new Integer(nr_tel);
        Integer id_temp = new Integer(orderId);
        Mail.send(informacje[0], informacje[1], informacje[2], informacje[3], ilosc, imie, nazwisko, email, tel_temp.toString(), cena, id_temp.toString(), pass.toString());
        
        TicketsDB tdb = new TicketsDB();
        tdb.open();
        for(int i = 0; i < ticket.size(); ++i){
            tdb.addTicket(orderId, informacje[1], informacje[2], Integer.parseInt(informacje[3]), miejsce.get(i)[0], miejsce.get(i)[1] , ticket.get(i).getTicketIndex() );
        }
        tdb.close();
    }
}

