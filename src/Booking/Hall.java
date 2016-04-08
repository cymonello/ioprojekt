/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Booking;

import database.TicketsDB;
import java.util.ArrayList;

/**
 *
 * @author Kamil Oleszek
 */
public class Hall {
    
    public static final int ROW = 10;
    public static final int COLUMN = 20;
    private int[][] Sala;
    private int id;
    
    public Hall(String d, String h, int id){
        TicketsDB tdb = new TicketsDB();
        tdb.open();
        Sala = tdb.checkHall(d, h, id);
        this.id = id;
        tdb.close();
    }
    
    public boolean selectSeat(int hall, int r, int c){
        if(Sala[r][c] == 0){ // 0 - wolne , 1 - zajete
            Sala[r][c] = 1;
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean checkSeat(int hall, int r, int c){
        if(Sala[r][c] == 0)
            return true;
        else return false;
    }
    
    public int[][] getHall(){
        return Sala;
    }
}
