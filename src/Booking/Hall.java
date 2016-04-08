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
    
    public static boolean selectSeat(int hall, int r, int c){
        int [][] Sala = getHall(hall);
        if(Sala[r][c] == 0){ // 0 - wolne , 1 - zajete
            Sala[r][c] = 1;
            return true;
        }
        else{
            return false;
        }
    }
    
    
    
    public static int[][] getHall(int id){
        switch(id){
            case 1: 
                return Sala1;
            default:
                return null;
        }
    }
    
    public static boolean checkSeat(int hall, int r, int c){
        int [][] Sala = getHall(hall);
        if(Sala[r][c] == 0)
            return true;
        else return false;
    }
}
