package Repertoire;

import database.TermsDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;



/**
 * Created by deleviretta on 23.03.16.
 */


/**
 * Klasa odpowiedzialna za operacje na repertuarze i obsługę funcjonalności, w które wymagają wyświetlania
 * dla danego terminu listy filmów wraz z godzinami.
 *
 */

public class Repertoire {
    private ResultSet moviesRS; //referencja do ResultSet z TermsDB
    private TermsDB tdb;
    private String date;
    private Integer[] hours; // godzina filmu
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
     * w szczególności tablice asocjacyjne
     * @param dateOfMovies data filmu
     * @throws ClassNotFoundException wyjątek klasy Class
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
     * Metoda pobierajaca z bazy danych wszystkie informacje o filmie: tytuł, rok wydania,
     * informacje o formie wyświetlania, język, czas trwania
     * @throws SQLException wyjątek bazodanowy
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
     * Prywatna metoda służąca do sprawdzenia czy wartość znajduje się w tablicy
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
     * Prywatna metoda, która tworzy tablicę godzin wyświetlania dla filmów wg ich ID
     * @param index indeks w tablicy movie, informuje dla jakiego filmu szukamy godzin
     * @throws SQLException wyjątek bazodanowy
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
     * Prywatna metoda, która tworzy tablicę z ID filmów wyświetlanych danego dnia
     * @throws SQLException wyjątek bazodanowy
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
     * Zwraca tablice przechowującą informacje o filmie: tytuł, rok wydania, informacje o formie wyświetlania,
     * język, czas trwania
     * @return tablica z informacji o filmie (dla każdego z nich)
     */
    public HashMap<Integer, String[]> getMapMovieInfo() {
        return mapMovieInfo;
    }

    /**
     * Zwraca tablicę przechowującą godziny wyświetlania danego filmu
     * @return tablica asocjacyjna zawierająca godziny wyświetlania dla danego filmu na dany dzień
     */
    public HashMap<Integer, Integer[]> getMapHours(){
        return mapHours;
    }

    /**
     * Zwraca tablice przechowującą numery ID filmów
     * @return tablica id filmów na dany dzień
     */
    public Integer[] getMovie(){
        return movie;
    }

    /**
     * Zwraca tablice ID
     * @return tablica asocjacyjna id terminów dla filmów
     */
    public HashMap<Integer, Integer[]> getMapTermsID() {
        return mapTermsID;
    }

    /**
     * Metoda, która w oparciu o tablicę asocjacyjną godzin i tablice id filmów tworzy wynikową tablice dwuwymiarową,
     * za pomocą, której tworzona jest tabelka w okienku repertuaru
     * @return tablica dwuwymiarowa zawierająca w wierszu wszystkie informacje o danym filmie: godziny i podstawowe
     * informacje
     */
    public String[][] getValue(){
        Integer temp = movie.length;
        String[][] table = new String[temp][18]; //12 godzin + 5 informacji
        String[] tempIntToString = new String[13]; //tablica godzin zamienionych na stringi
        Integer[] tempInt; //tymczasowa tablica intów przechowujaca godziny
        String[] tempString; //tymczasowa tablica przechowująca wartość z tablicy asocjacyjnej
        String[] connectedRow = new String[18]; // tablica przechowująca cały wiersz
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
            for (int j = 5; j < 18; j++) {
                connectedRow[j] = tempIntToString[j-5];
            }
            for (int k = 0; k < 18; k++) {
                table[i][k] = connectedRow[k];
            }
        }
        return table;
    }

    /**
     * Zwraca ID filmu
     * @param row wiersz filmu
     * @return konkretne ID filmu
     */
    public Integer getMovieID(Integer row){
        return movie[row];
    }

    /**
     * Metoda zwracająca id terminu
     * @param row wiersz w tabeli
     * @param column kolumna tabeli
     * @return konkretne id terminu, które występuje w bazie
     */
    public Integer getTermID(Integer row, Integer column){
        Integer[] tempTable = mapTermsID.get(movie[row]);
        return tempTable[column-5];
    }
}
