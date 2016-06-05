package Booking;

import database.TicketsDB;

/**
 *
 * @author Kamil Oleszek
 */
public class Hall {

    public static final int ROW = 10;
    public static final int COLUMN = 20;
    private int[][] Sala;
    private int id;

    public Hall(String d, String h, int id) {
        TicketsDB tdb = new TicketsDB();
        tdb.open();
        Sala = tdb.checkHall(d, h, id);
        this.id = id;
        tdb.close();
    }

    public boolean selectSeat(int hall, int r, int c) {
        if (Sala[r][c] == 0) { // 0 - wolne , 1 - zajete
            Sala[r][c] = 1;
            return true;
        } else {
            return false;
        }
    }

    public boolean checkSeat(int hall, int r, int c) {
        if (Sala[r][c] == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int[][] getHall() {
        return Sala;
    }
}
