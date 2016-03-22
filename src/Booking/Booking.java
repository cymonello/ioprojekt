/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Booking;
//import java.util.*;


/**
 *
 * @author Kamil Oleszek
 */
public class Booking {
    Ticket ticket;
    Film film;
    Client client;
    Seat seat;
}

class Ticket{
    static String[] types = {"Normalny", "Szkolny", "Seniorski", "Studencki"} ;
    String type;
    int amount;
    static int total_amount;
    double price;
    static double sum;
    Client client;
    Seat seat;
    Ticket(int index, int ilosc){
        type = types[index];
        amount = ilosc;
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
        sum = amount * price;
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
    final static int ROW = 10;
    final static int COLUMN = 20;
    int row;
    int column;
    static boolean[][] seats = new boolean[ROW][COLUMN];
    static enum miejsca {szary, zielony, zolty };
    Seat(int r, int c){
        if(seats[r][c] == false){
            row = r;
            column = c;
            seats[r][c] = true;
        }
        else{
            System.out.println("To miejsce jest zajete, sprobuj wybrac inne.");
        }
    
    }

}