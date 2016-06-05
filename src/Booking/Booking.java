package Booking;

import Email.Mail;
import java.sql.Connection;
import java.sql.Statement;
import database.OrdersDB;
import database.TermsDB;
import database.TicketsDB;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Klasa Booking reprezentujaca rezerwacje biletu na wybrany film, w wybranym
 * terminie i o wybranej godzinie Przechowuje liste biletow, liste miejsc(w
 * formacie: [rząd, kolumna]), numer identyfikacyjny filmu, ktory jest
 * przypisany do danego terminu i godziny oraz informacje o rezerwacji(tytul,
 * data, godzina, sala).
 *
 * @author Kamil Oleszek
 */
public class Booking {

    //metoda tworzaca obiekty bilet w petli wg ilosci tego co wpisane w formularzu
    private List<Ticket> ticket = new ArrayList<Ticket>();
    private ArrayList<int[]> miejsce = new ArrayList<>();
    private Connection connect = null;
    private Statement statement = null;
    private int termId;
    private String[] informacje;

    /**
     * Metoda, ktora zwraca ilosc kupionych biletow
     *
     * @return ticket.size() - rozmiar listy ticket
     */
    public int listLength() {
        return ticket.size();
    }

    /**
     * Metoda, ktora udostepnia wszystkie informacje na temat rezerwacji -
     * zgromadzone w tablicy informacje
     *
     * @return informacje - tablica informacji(skladowa)
     */
    public String[] getInfo() {
        return informacje;
    }

    /**
     * Metoda, ktora dodaje do listy biletow kolejny obiekt klasy Ticket
     *
     * @param index - numer identyfikacyjny biletu(typ)
     */
    public void newTicket(int index) {
        ticket.add(new Ticket(index));
    }

    /**
     * Metoda, ktora zwraca calkowity koszt zamowienia, jesli ilosc biletow jest
     * równa co najmniej 5 to klient otrzymuje znizke -30% od kazdego biletu
     *
     * @return cena - calkowity koszt zamowionych biletow
     */
    public double price() {
        double cena = 0;
        if (ticket.size() >= 5) {
            for (int i = 0; i < ticket.size(); ++i) {
                cena += 0.7 * ticket.get(i).koszt();
            }
        } else {
            for (int i = 0; i < ticket.size(); ++i) {
                cena += ticket.get(i).koszt();
            }
        }
        return cena;
    }

    /**
     * Metoda, ktora zwraca tablice cen kazdego typu biletu.
     *
     * @return ceny - tablica cen kazdego typu biletu
     */
    public double[] ceny() {
        double[] ceny = new double[4];
        ceny[0] = 24.00;
        ceny[1] = 17.00;
        ceny[2] = 18.00;
        ceny[3] = 17.00;

        return ceny;
    }

    /**
     * Konstruktor klasy Booking
     */
    public Booking() {
    }

    /**
     * Metoda, ktora umozliwia rozpoczecie procesu rezerwacji Jest ona
     * wywolywana po kliknieciu w godzine pod wybranym filmem.
     *
     * Do skladowej termId przypisuje numer id filmu(ktory jest przypisany do
     * danego terminu i danej godziny)
     *
     * @param id - numer id filmu(ktory jest przypisany do danego terminu i
     * danej godziny)
     */
    public void startBooking(int id) {
        TermsDB term = new TermsDB();
        term.open();
        informacje = term.getTermInfo(id);
        this.termId = id;
        term.close();
    }

    /**
     * Metoda, dzieki ktorej pozyskuje liste wszystkich zarezerwowanych miejsc i
     * przypisuje ja do skladowej miejsce
     *
     * @param msc - lista zarezerwowanych miejsc w formacie: [rząd, kolumna]
     */
    public void getSeat(ArrayList<int[]> msc) {
        miejsce = msc;
    }

    /**
     * Metoda, ktora konczy proces rezerwacji i dodaje zamowienie do bazy
     * OrdersDB za pomoca metody addOrder. Dodaje takze zarezerwowane bilety do
     * bazy TicketDB za pomoca metody addTicket Metoda ta generuje takze numer
     * rezerwacji oraz wysyla maila z potwierdzeniem zamowienia
     *
     *
     * @param imie - imie
     * @param nazwisko - nazwisko
     * @param email - adres e-mail
     * @param nr_tel - numer telefonu
     * @param ilosc - ilosc biletow
     * @param cena - calkowity koszt biletow
     * @return true - jesli rezerwacja sie powiodla, false - jesli sie nie
     * powiodla.
     */
    public boolean endBooking(String imie, String nazwisko, String email, int nr_tel, String ilosc, String cena) {
        Random rand1 = new Random();
        Integer pass = rand1.nextInt(899999) + 100000;
        Hall sala = new Hall(informacje[1], informacje[2], Integer.parseInt(informacje[3]));
        boolean ok = true;
        for (int i = 0; i < ticket.size(); ++i) {
            if (sala.checkSeat(Integer.parseInt(informacje[3]), miejsce.get(i)[0], miejsce.get(i)[1]) == false) {
                ok = false;
                break;
            }
        }
        if (ok == true) {
            OrdersDB odb = new OrdersDB();
            odb.open();

            odb.addOrder(termId, imie, nazwisko, email, nr_tel, pass.toString());
            int orderId = odb.getId();
            odb.close();

            //Wysłanie maila z potwierdzeniem
            Integer tel_temp = new Integer(nr_tel);
            Integer id_temp = new Integer(orderId);
            Mail.send(informacje[0], informacje[1], informacje[2], informacje[3], ilosc, imie, nazwisko, email, tel_temp.toString(), cena, id_temp.toString(), pass.toString());

            TicketsDB tdb = new TicketsDB();
            tdb.open();
            for (int i = 0; i < ticket.size(); ++i) {
                tdb.addTicket(orderId, informacje[1], informacje[2], Integer.parseInt(informacje[3]), miejsce.get(i)[0], miejsce.get(i)[1], ticket.get(i).getTicketIndex());
            }
            tdb.close();
            return true;
        }
        return false;
    }
}
