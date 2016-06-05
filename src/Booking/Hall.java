package Booking;

import database.TicketsDB;

/**
 * Klasa reprezentujaca sale kinowa. Sala jest reprezentowana jako tablica
 * dwuwymiarowa wartosci int (-1: przejscie, 0: wolne, 1: zajete). Rozmiar
 * kazdej sali to 200 miejsc (10x20). Kazda sala posiada swoj numer
 * identyfikacyjny.
 *
 * @author Kamil Oleszek
 */
public class Hall {

    public static final int ROW = 10;
    public static final int COLUMN = 20;
    private int[][] Sala;
    private int id;

    /**
     * Konstruktor klasy Hall - otwiera baze biletow i za pomoca metody
     * checkHall uzyskuje aktualny stan sali kinowej
     *
     * @param d - data filmu
     * @param h - godzina rozpoczecia filmu
     * @param id - numer id sali
     */
    public Hall(String d, String h, int id) {
        TicketsDB tdb = new TicketsDB();
        tdb.open();
        Sala = tdb.checkHall(d, h, id);
        this.id = id;
        tdb.close();
    }

    /**
     * Metoda ktora umozliwia wybranie miejsca na sali (jesli jest wolne)
     *
     * @param hall - numer id sali
     * @param r - numer wiersza(rzedu)
     * @param c - numer kolumny(miejsca w rzedzie)
     * @return - true jesli udalo sie wybrac żądane miejsce, false jesli żądane
     * miejsce jest zajęte i nie udalo sie wybrac
     */
    public boolean selectSeat(int hall, int r, int c) {
        if (Sala[r][c] == 0) { // 0 - wolne , 1 - zajete
            Sala[r][c] = 1;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metoda, ktora sprawdza czy dane miejsce jest wolne.
     *
     * @param hall - numer id sali
     * @param r - numer wiersza(rzedu)
     * @param c - numer kolumny(miejsca w rzedzie)
     * @return - true jesli dane miejsce jest wolne, false jesli jest zajete.
     */
    public boolean checkSeat(int hall, int r, int c) {
        if (Sala[r][c] == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metoda, ktora udostepnia aktualny stan sali (dwuwymiarowa tablica int)
     *
     * @return Sala - referencja do dwuwymiarowej tablicy intow (reprezentacja
     * sali)
     */
    public int[][] getHall() {
        return Sala;
    }
}
