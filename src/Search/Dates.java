package Search;

import Repertoire.Repertoire;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by deleviretta on 22.04.16.
 */
/**
 * Klasa odpowiadająca za stworzenie repertuaru z datami wyświetlania dla danego
 * filmu. Obiekt wykorzystywany w wyszukiwarce po wciśnieciu przycisku "Sprawdź
 * terminy"
 */
public class Dates {

    /**
     * Tablica przechowująca daty filmów
     */
    private String[] datesOfMovie;
    /**
     * Tablica asocjacyjna, która dla danego terminu trzyma tablice godzin
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

    /**
     * Konstruktor przyjmujący id filmu dla którego będziemy szukali dat jego
     * wyświetlania
     */
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
        } catch (SQLException | ClassNotFoundException e) {
        }
    }

    /**
     * Prywatna metoda tworząca tablicę dat filmów
     *
     * @throws SQLException wyjątek bazodanowy
     * @throws ClassNotFoundException wyjątek klasy Class
     */
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
            if (idPerDay != null) {
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

    /**
     * Zwraca tablicę przechowującą daty
     *
     * @return tablica dat
     */
    public String[] getDatesOfMovie() {
        return datesOfMovie;
    }

    /**
     * Zwraca tablice asocjacyjną jako HashMap przechowującą godziny dla dat
     *
     * @return tablica asocjacyjna z godzinami wyświetlania dla dat
     */
    public HashMap<String, Integer[]> getMapHoursOfMovie() {
        return mapHoursOfMovie;
    }

    private boolean contains(Integer[] table, Integer value) {
        for (int i = 0; i < table.length; i++) {
            if (table[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metoda zwracająca id terminu (dla konkretnej daty i godziny), który
     * znajduje się w bazie danych
     *
     * @param hours godzina wyświetlania
     * @param date data wyświetlania
     * @return id terminu z bazy
     */
    public Integer gettingTermsIDForFilm(Integer hours, String date) {
        Integer[] tempHours = mapHoursOfMovie.get(date);
        Integer[] tempTermID = mapTermsOfMovie.get(date);
        Integer tempIndex;
        for (int i = 0; i < tempHours.length; i++) {
            if (tempHours[i].equals(hours)) {
                tempIndex = i;
                return tempTermID[tempIndex];
            }
        }
        return null;
    }
}
