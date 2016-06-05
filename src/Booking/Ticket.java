package Booking;

/**
 * Klasa reprezentujaca bilet Zawiera nastepujace informacje: typ, cene, numer
 * id Zawiera informacje ogolne(jako pola statyczne) : calkowita ilosc biletow,
 * calkowita cena, typy biletow jako tablica String
 *
 * @author Kamil Oleszek
 */
public class Ticket {

    private String type;
    private double price;
    static int total_amount;
    static double total_sum;
    private int ticketIndex;
    static String[] types = {"Normalny", "Szkolny", "Seniorski", "Stsudencki"};

    /**
     * Konstruktor klasy Ticket, tworzy obiekt na podstawie numeru id, ktory
     * oznacza typ biletu(0: Normalny, 1: Szkolny, 2: Seniorski, 3: Studencki)
     * Na podstawie wybranego typu biletu przypisuje cene, zwiększa calkowita
     * ilosc biletow i calkowita cene.
     *
     * @param index - indeks wybranego biletu
     */
    Ticket(int index) {
        ticketIndex = index;
        type = types[index];
        switch (index) {
            case 0:
                price = 24.00;
                break;
            case 3:
            case 1:
                price = 17.00;
                break;
            case 2:
                price = 18.00;
                break;
        }
        ++total_amount;
        total_sum += price;
    }

    /**
     * Metoda, ktora zwraca cene danego biletu
     *
     * @return price - cena danego biletu(skladowa)
     */
    public double koszt() {
        return price;
    }

    /**
     * Metoda, ktora zwraca numer id biletu
     *
     * @return ticketIndex - numer id danego biletu(skladowa)
     */
    public int getTicketIndex() {
        return ticketIndex;
    }

}
