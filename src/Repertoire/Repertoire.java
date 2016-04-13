package Repertoire;

import database.TermsDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;



/**
 * Created on 23.03.16.
 */


public class Repertoire {
    private ResultSet moviesRS; //referencja do ResultSet z TermsDB
    private TermsDB tdb;
    String date;
    private Integer[] hours; // godzina filmuhttps://www.facebook.com/groups/620900221394681/631541116997258/
    private Integer[] movie; //tablica id filmow na dany dzien
    private Integer[] termsID;
    // tablica asocjacyjna przechowujaca termID dla movieID
    private HashMap<Integer, Integer[]> mapTermsID = new HashMap<>();
    // tablica asocjacyjna przechowujaca informacje o filmie, klucz movie(id), wartość movieInfo
    private HashMap<Integer, String[]> mapMovieInfo = new HashMap<>();
    // tablica asocjacyjna przechowujaca godziny dla danego filmu, klucz - id filmu, wartość tablica z godzinami
    private HashMap<Integer, Integer[]> mapHours = new HashMap<>();

    /**
     * Konstruktor przyjmujący date filmu, tworzy ResultSet dla bazy Terms, skąd zostaną następnie pobrane ID
     * filmów przyjmuje datę jako String, wywoluje sie rowniez w nim funkcja, ktora inicjalizuje pozostaje pola,
     * w szczególności tablice asocjacyjne za pomocą metody gettingInfo()
     *
     * @param dateOfMovies data filmu
     * @throws ClassNotFoundException
     */


    public Repertoire(String dateOfMovies) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            tdb = new TermsDB();
            date = dateOfMovies;
            tdb.open();
            moviesRS = tdb.getTermsInDay(dateOfMovies);
            gettingInfo();
            tdb.close();
        }
        catch(ClassNotFoundException e){
        }
    }

    /**
     * Metoda pobierajaca wszystkie informacje o filmie
     *
     * @throws SQLException
     */


    private void gettingInfo() throws SQLException {
        gettingMovieId();
        for (int i = 0; i < movie.length; i++) {
            if (!mapMovieInfo.containsKey(movie[i])) {
                mapMovieInfo.put(movie[i], tdb.getMovieInfo(movie[i]));
                gettingHours(i);
                mapHours.put(movie[i], hours);
                mapTermsID.put(movie[i], termsID);
            }
        }
    }

    /**
     * Metoda służąca do sprawdzenia czy wartość znajduje się w tablicy
     * @param tab tablica wartości
     * @param key szukana wartość
     * @return zwraca liczbę dodatnią gdy wartość jest w tablicy, ujemną gdy wartość jest poza tablicą
     */
    private Integer searchingIfValue(Integer[] tab, Integer key){
        for (int i = 0; i < tab.length; i++) {
            if(tab[i]==key){
                return 1;
            }
        }
        return -1;
    }

    /**
     * Metoda, która tworzy tablicę godzin wyświetlania dla filmów wg ich ID
     * @param index indeks w tablicy movie, informuje dla jakiego filmu szukamy godzin
     * @throws SQLException
     */

    private void gettingHours(Integer index) throws SQLException{
        int k = 0;
        Integer id;
        hours = new Integer[13];
        termsID = new Integer[13];
        Integer[] tempHours = new Integer[13];
        Integer[] tempID = new Integer[13];
        while (moviesRS.next()) {
            id = moviesRS.getInt("movie");
            if (id == movie[index]) {
                tempHours[k] = moviesRS.getInt("hour");
                tempID[k] = moviesRS.getInt("id");
                k++;
            }
        }
        Integer tempIndex =0;
        for (int ii = 10; ii <= 22; ii++) {
            for (int l = 0; l < tempHours.length; l++) {
                if(tempHours[l]!=null){
                    if(tempHours[l]==ii){
                        hours[tempIndex]=tempHours[l];
                        termsID[tempIndex]=tempID[l];
                    }
                }
            }
            if(hours[tempIndex]==null){
                hours[tempIndex]=0;
                termsID[tempIndex]=0;
            }
            tempIndex++;
        }
        moviesRS.beforeFirst();
    }

    /**
     * Metoda, która tworzy tablicę z ID filmów wyświetlanych danego dnia
     *
     * @throws SQLException
     */
    private void gettingMovieId() throws SQLException {
        Integer j = 0;
        Integer[] tempMovie = new Integer[20];
        while (moviesRS.next()) {
            if (searchingIfValue(tempMovie, moviesRS.getInt("movie")) < 0) {
                tempMovie[j] = moviesRS.getInt("movie");
                j++;
            }
        }
        movie = new Integer[j];
        for (int i = 0; i < movie.length; i++) {
            movie[i]=tempMovie[i];
        }
        moviesRS.beforeFirst();
    }

    /**
     * Getter dla tablicy przechowujące informacje o filmie: title, age, info, lang, duration
     *
     * @return tablica asocjacyjna, HashMap
     */
    public HashMap<Integer, String[]> getMapMovieInfo() {
        return mapMovieInfo;
    }

    /**
     * Getter dla tablicy przechowującej godziny wyświetlania danego filmu
     *
     * @return tablica asocjacyjna, HashMap
     */

    public HashMap<Integer, Integer[]> getMapHours(){
        return mapHours;
    }

    /**
     * Getter dla tablicy przechowującej numery ID filmów
     *
     * @return tablica Integerów
     */
    public Integer[] getMovie(){
        return movie;
    }

    /**
     * Getter dla tablicy ID
     * @return
     */
    public HashMap<Integer, Integer[]> getMapTermsID() {
        return mapTermsID;
    }

    /**
     * Metoda, ktora w oparciu o HashMap i tablice Integerów(movie) tworzy wynikową tablice dwuwymiarową,
     * za pomocą, której tworzona jest tabelka w okienku repertuaru
     *
     * @return tablica dwuwymiarowa zawierająca w wierszu wszystkie informacje o danym filmie
     */
    public String[][] getValue(){
        Integer temp = movie.length;
        String[][] table = new String[temp][17]; //12 godzin + 5 informacji
        String[] tempIntToString = new String[12]; //tablica godzin zamienionych na stringi
        Integer[] tempInt; //tymczasowa tablica intów przechowujaca godziny
        String[] tempString; //tymczasowa tablica przechowująca wartość z tablicy asocjacyjnej
        String[] connectedRow = new String[17]; // tablica przechowująca cały wiersz
        for (int i = 0; i < movie.length; i++) {
            tempInt = mapHours.get(movie[i]);
            for (int j = 0; j < tempIntToString.length; j++) {
                if(tempInt[j]==0){
                    tempIntToString[j] = " ";
                }
                else
                    tempIntToString[j] = String.valueOf(tempInt[j]);
            }
            tempString = mapMovieInfo.get(movie[i]);
            for (int j = 0; j < 5; j++) {
                connectedRow[j] = tempString[j];
            }
            for (int j = 5; j < 17; j++) {
                connectedRow[j] = tempIntToString[j-5];
            }
            for (int k = 0; k < 17; k++) {
                table[i][k] = connectedRow[k];
            }
        }
        return table;
    }

    /**
     *Getter dla ID filmu
     *
     * @param row
     * @return
     */
    public Integer getMovieID(Integer row){
        return movie[row];
    }
    public Integer getTermID(Integer row, Integer column){
        Integer[] tempTable = mapTermsID.get(movie[row]);
        return tempTable[column-5];
    }
}
