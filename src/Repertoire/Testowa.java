package Repertoire;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created on 23.03.16.
 */
public class Testowa {
    public static void main(String[] args) throws SQLException {
        try {
            Repertoire repertuar = new Repertoire("20.05.16");
            HashMap<Integer, String[]> aboutMovie = repertuar.getMapMovieInfo();
            HashMap<Integer, Integer[]> displayingHours = repertuar.getMapHours();
            Integer[] movieID = repertuar.getMovie();
            HashMap<Integer, Integer[]> displayID = repertuar.getMapTermsID();
            int i = 0;
            while(i<movieID.length){
                Integer[] termID = displayID.get(movieID[i]);
                for (int j = 0; j < termID.length; j++) {
                    System.out.print(termID[j] + " ");
                }
                System.out.println();
                i++;
            }
            for (int k = 0; k < movieID.length; k++) {
                String[] info = aboutMovie.get(movieID[k]);
                for (int j = 0; j < info.length; j++) {
                    System.out.print(info[j] + " ");
                }
                Integer[] hours = displayingHours.get(movieID[k]);
                for (int j = 0; j < hours.length; j++) {
                    System.out.print(hours[j] + " ");
                }
                System.out.println();
            }
            System.out.println();
            String[][] tablica = repertuar.getValue();
            for (int k = 0; k < movieID.length; k++) {
                for (int j = 0; j < 18; j++) {
                    System.out.print(tablica[k][j] + " ");
                }
                System.out.println();
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
