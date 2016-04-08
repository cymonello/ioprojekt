/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Booking;

import database.HallDB;
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
    
    public Hall(int id){
        HallDB hdb = new HallDB();
        hdb.open();
        Sala = hdb.getHall(id);
        this.id = id;
        hdb.close();
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
    
    public void updateHall(ArrayList<int[]> miejsca){
        HallDB hdb = new HallDB();
        hdb.open();
        for(int i = 0; i < miejsca.size(); ++i){
            Sala[miejsca.get(i)[0]][miejsca.get(i)[1]]=1;
        }
        hdb.updateHall(id, Sala);
        hdb.close();
    }
    
    public int[][] getHall(){
        return Sala;
    }
}
