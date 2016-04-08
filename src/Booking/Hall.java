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
public class Hall {
    public static final int ROW = 10;
    public static final int COLUMN = 20;
    private static int[][] Sala1 = new int[ROW][COLUMN];
    
    public static boolean selectSeat(int r, int c){
        if(Sala1[r][c] == 1){ // 1 - wolne , 0 - zajete
            Sala1[r][c] = 0;
            return true;
        }
        else{
            return false;
        }
    }
    
    public int[][] getHall(int id){
        switch(id){
            case 1: 
                return Sala1;
            default:
                return null;
        }
    }
    
    
    
}
