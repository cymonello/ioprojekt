package Search;

import Repertoire.Repertoire;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by deleviretta on 22.04.16.
 */
public class Dates {
    /**
     * Tablica przechowująca daty filmów
     */
    private String[] datesOfMovie;
    /**
     * Tablica asocjacyjna, która dla danego terminu trzyma tablice godzin o zmiennej długości
     */
    private HashMap<String, Integer[]> mapHoursOfMovie;
    /**
     * Tablica ascojacyjna, która dla danego terminu trzyma tablice termID
     */
    private HashMap<String, Integer[]> mapTermsOfMovie;
    /**
     * Zmienna przechowująca ID filmu dla, którego szukamy terminów
     */
    private Integer movieID;
    private Calendar cal = Calendar.getInstance(); //tworzenie kalendarza
    private SimpleDateFormat s = new SimpleDateFormat("dd.MM.yy"); //formatowanie daty
    /**
     * Tablica terminów na 6 dni do przodu
     */
    private String[] date = new String[7];
    private Integer numberOfDays;

    public Dates(Integer id) {
        numberOfDays = 7;
        date[0] = s.format(cal.getTime());
        movieID = id;
        mapHoursOfMovie = new HashMap<>();
        mapTermsOfMovie = new HashMap<>();
        for (int i = 1; i < numberOfDays; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            date[i] = s.format(cal.getTime());
        }
        try {
            buildingTables();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void buildingTables() throws SQLException, ClassNotFoundException {
        String[] tempDatesOfMovies = new String[7];
        Repertoire rep;
        int j = 0;
        Integer[] idPerDay;
        HashMap<Integer, Integer[]> hours;
        HashMap<Integer, Integer[]> termsID;
        for (int i = 0; i < numberOfDays; i++) {
            rep = new Repertoire(date[i]);
            idPerDay = rep.getMovie();
            if(idPerDay!=null) {
                if (contains(idPerDay, movieID)) {
                    tempDatesOfMovies[j] = date[i];
                    hours = rep.getMapHours();
                    termsID = rep.getMapTermsID();
                    mapHoursOfMovie.put(tempDatesOfMovies[j], hours.get(movieID));
                    mapTermsOfMovie.put(tempDatesOfMovies[j], termsID.get(movieID));
                    j++;
                }
            }
        }
        datesOfMovie = new String[j];
        for (int i = 0; i < j; i++) {
            datesOfMovie[i] = tempDatesOfMovies[i];
        }
    }

    public String[] getDatesOfMovie() {
        return datesOfMovie;
    }

    public HashMap<String, Integer[]> getMapHoursOfMovie() {

        return mapHoursOfMovie;
    }
    private boolean contains(Integer[] table, Integer value){
        for (int i = 0; i < table.length; i++) {
            if(table[i].equals(value)) {
                return true;
            }
        }
        return false;
    }
    public Integer gettingTermsIDForFilm (Integer hours, String date){
        Integer[] tempHours = mapHoursOfMovie.get(date);
        Integer[] tempTermID = mapTermsOfMovie.get(date);
        Integer tempIndex;
        for (int i = 0; i < tempHours.length; i++) {
            if(tempHours[i].equals(hours)){
                tempIndex = i;
                return tempTermID[tempIndex];
            }
        }
        return null;
    }
}
