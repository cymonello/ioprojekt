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
     * Zmienna przechowująca ID filmu dla, którego szukamy terminów
     */
    private Integer movieID;
    private Calendar cal = Calendar.getInstance(); //tworzenie kalendarza
    private SimpleDateFormat s = new SimpleDateFormat("dd.MM.yy"); //formatowanie daty
    /**
     * Tablica terminów na 6 dni do przodu
     */
    private String[] date = new String[6];
    private Integer numberOfDays;

    public Dates(Integer id) {
        numberOfDays = 6;
        date[0] = s.format(cal.getTime());
        movieID = id;
        mapHoursOfMovie = new HashMap<>();
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
        for (int i = 0; i < numberOfDays; i++) {
            rep = new Repertoire(date[i]);
            idPerDay = rep.getMovie();
            if(idPerDay!=null) {
                if (contains(idPerDay, movieID)) {
                    tempDatesOfMovies[j] = date[i];
                    hours = rep.getMapHours();
                    mapHoursOfMovie.put(tempDatesOfMovies[j], hours.get(movieID));
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
}
