package Repertoire;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created on 23.03.16.
 */
public class Testowa {
    public static void main(String[] args) throws SQLException {
        try {
            Repertoire repertuar = new Repertoire("24.03.16");
            HashMap<Integer, String[]> aboutMovie = repertuar.getMapMovieInfo();
            HashMap<Integer, Integer[]> displayingHours = repertuar.getMapHours();
            Integer[] movieID = repertuar.getMovie();
            for (int i = 0; i < movieID.length; i++) {
                String[] info = aboutMovie.get(movieID[i]);
                for (int j = 0; j < info.length; j++) {
                    System.out.print(info[j] + " ");
                }
                Integer[] hours = displayingHours.get(movieID[i]);
                for (int j = 0; j < hours.length; j++) {
                    System.out.print(hours[j] + " ");
                }
            }
            System.out.println();
            String[][] tablica = repertuar.getValue();
            for (int i = 0; i < movieID.length; i++) {
                for (int j = 0; j < 17; j++) {
                    System.out.print(tablica[i][j] + " ");
                }
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
