package Repertoire;

import database.TermsDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Created on 23.03.16.
 */
public class Repertoire {
    ResultSet movies; //referencja do ResultSet z TermsDB
    TermsDB tdb;
    String date; //data
    Integer hall; //sala
    Integer[] hours; // godzina filmu
    Integer[] movie; //tablica id filmow na dany dzien
    Integer id; //przechwowuje id terms
    // tablica asocjacyjna przechowujaca informacje o filmie, klucz movie(id), wartość movieInfo
    HashMap<Integer, String[]> mapMovieInfo = new HashMap<>();
    // tablica asocjacyjna przechowujaca godziny dla danego filmu, klucz - id filmu, wartość tablica z godzinami
    HashMap<Integer, Integer[]> mapHours = new HashMap<>();
    /**
     * Konstruktor przyjmujący date filmu, tworzy ResultSet dla bazy Terms, skąd zostaną następnie pobrane ID filmów
     * przyjmuje datę jako String
     *
     * @param dateOfMovies data filmu
     * @throws ClassNotFoundException
     */


    public Repertoire(String dateOfMovies) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            tdb = new TermsDB();
            date = dateOfMovies;
            hours = new Integer[12];
            tdb.open();
            movies = tdb.getTermsInDay(dateOfMovies);
            tdb.close();
            gettingInfo();
        }
        catch(ClassNotFoundException e){
            e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pobierajaca wszystkie informacje o filmie
     *
     * @throws SQLException
     */


    private void gettingInfo() throws SQLException {
        tdb.open();
        Integer i = 0;
        Integer j = 0;
        while(movies.next()){
            i+=1;
        }
        movies.beforeFirst();
        while(movies.next()){
            ResultSet temp = movies;
            id = movies.getInt("id");
            hall = movies.getInt("hall");
            if(Arrays.binarySearch(movie, movies.getInt("movie"))>=0) {
                movie[j] = movies.getInt("movie");
            }
            if(!mapMovieInfo.containsKey(movie[j])){
                mapMovieInfo.put(movie[j],tdb.getMovieInfo(movie[j]));
                int k = 0;
                while(movies.next()){
                    if(id==movies.getInt("id")){
                        hours[k]=movies.getInt("id");
                        k++;
                    }
                }
                mapHours.put(movie[j], hours);
                j++;
            }
            movies.beforeFirst();
        }

        tdb.close();
    }

}
