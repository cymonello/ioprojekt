package Windows;

import java.awt.Color;

/**
 * Enum odpowiedzialny za zestawy kolorów jakie możeby brać urzytkownik. Są 4
 * podstawowe schematy kolorystyczne Urzytkownik nie może tworzyć własnych
 * nowych Można jedynie wybierać schetaty stworzone już
 *
 * @author Bartłomiej
 */
public enum Colors {

    /**
     * Schemat podstawowy, wybierany automatycznie Dominują w nim kolory czarne
     * (tło) oraz jasne kolory dla tekstów
     */
    SCHEMAT0(Color.black, //tlo
            new Color(217, 252, 208), //nazwy
            Color.white, //napisy
            Color.blue, //podswietlenie
            new Color(19, 51, 10), //tabelkaHeader
            new Color(35, 74, 24), //tabelkaTlo
            new Color(229, 206, 136), //tabelkaNapisy
            Color.gray, //datyTlo
            Color.white, //datyNapisy
            new Color(42, 38, 36), //scroll
            new Color(28, 114, 5) //ramkaWyszkiwania
    ),
    SCHEMAT1(new Color(61, 255, 97), //tlo
            new Color(178, 7, 36), //nazwy
            new Color(147, 88, 255), //napisy
            new Color(28, 204, 160), //podswietlenie
            new Color(188, 28, 204), //tabelkaHeader
            new Color(98, 153, 139), //tabelkaTlo
            new Color(229, 206, 136), //tabelkaNapisy
            new Color(121, 182, 255), //datyTlo
            new Color(229, 206, 136), //datyNapisy
            new Color(28, 114, 5), //scroll
            new Color(28, 114, 5) //ramkaWyszkiwania
    ),
    SCHEMAT2(new Color(221, 194, 255), //tlo
            new Color(178, 7, 36), //nazwy
            new Color(255, 254, 194), //napisy
            new Color(19, 80, 204), //podswietlenie
            new Color(55, 49, 64), //tabelkaHeader
            new Color(110, 103, 120), //tabelkaTlo
            new Color(229, 206, 136), //tabelkaNapisy
            new Color(110, 103, 120), //datyTlo
            new Color(229, 206, 136), //datyNapisy
            new Color(130, 61, 184), //scroll
            new Color(130, 61, 184) //ramkaWyszkiwania
    ),
    SCHEMAT3(new Color(205, 172, 164), //tlo
            new Color(58, 116, 83), //nazwy
            new Color(127, 61, 58), //napisy
            new Color(255, 255, 255), //podswietlenie
            new Color(85, 62, 57), //tabelkaHeader
            new Color(128, 97, 90), //tabelkaTlo
            new Color(255, 255, 255), //tabelkaNapisy
            new Color(85, 62, 57), //datyTlo
            new Color(179, 210, 255), //datyNapisy
            new Color(255, 0, 0), //scroll
            new Color(255, 83, 81) //ramkaWyszkiwania
    );

    private final Color kolorTla;
    private final Color kolorNapisow;
    private final Color kolorNazw;
    private final Color kolorPodswietlania;
    private final Color kolorTabelkaHeader;
    private final Color kolorTabelkaTlo;
    private final Color kolorTabelkaNapisy;
    private final Color kolorDatyTlo;
    private final Color kolorDatyNapisy;
    private final Color kolorScroll;
    private final Color kolorRamkaWyszukiwania;

    /**
     * Konstruktor twozrący zestawy schemtów kolorystycznych
     *
     * @param tlo - kolor tła
     * @param nazwy - kolor tekstu dla ważnych nazw (np.: tytuł filmy, rodzaje
     * biletów przy wyborze ich ilości)
     * @param napisy - podstawowy kolor dla napisów wyświetlanych w oknie
     * programu
     * @param podswietlanie - kolor napisów które umżliwiają kliknięcie na sibie
     * powodujące wyświetlenie nowego okna, kolor ten zmiania się po najechaniu
     * na napis
     * @param tabelkaHeader - kolor tla pierwszego wiersza tabelki w repertuarze
     * ( tytuł, wiek, napisy ,godziny, itd...)
     * @param tabelkaTlo - kolor tła tabelki z danymi w repertuarze (bez
     * pierwszego wierrsza)
     * @param tabelkaNapisy - kolor napisów w tabelce w repertuarze
     * @param datyTlo - kolor tła przycisk umożliwiającego wybór daty w oknie
     * repertuaru
     * @param datyNapisy - kolor napisów dla przycisku wyboru daty w oknie
     * repertuaru
     * @param scroll - kolor wszystkich scrolli jaie występują w programie
     * @param ramkaWyszukiwania - obrazmowanie pola wyszukiwarki
     */
    Colors(Color tlo, Color nazwy, Color napisy, Color podswietlanie, Color tabelkaHeader, Color tabelkaTlo, Color tabelkaNapisy,
            Color datyTlo, Color datyNapisy, Color scroll, Color ramkaWyszukiwania) {
        kolorTla = tlo;
        kolorNapisow = napisy;
        kolorNazw = nazwy;
        kolorPodswietlania = podswietlanie;
        kolorTabelkaHeader = tabelkaHeader;
        kolorTabelkaTlo = tabelkaTlo;
        kolorTabelkaNapisy = tabelkaNapisy;
        kolorDatyTlo = datyTlo;
        kolorDatyNapisy = datyNapisy;
        kolorScroll = scroll;
        kolorRamkaWyszukiwania = ramkaWyszukiwania;
    }

    public Color getTlo() {
        return kolorTla;
    }

    public Color getPodswietlony() {
        return kolorPodswietlania;
    }

    public Color getNapisy() {
        return kolorNapisow;
    }

    public Color getNazwy() {
        return kolorNazw;
    }

    public Color getKolorTabelkaHeader() {
        return kolorTabelkaHeader;
    }

    public Color getKolorTabelka() {
        return kolorTabelkaTlo;
    }

    public Color getKolorTabelkaNapisy() {
        return kolorTabelkaNapisy;
    }

    public Color getKolorDatyNapisy() {
        return kolorDatyNapisy;
    }

    public Color getKolorDatyTlo() {
        return kolorDatyTlo;
    }

    public Color getKolorScroll() {
        return kolorScroll;
    }

    public Color getKolorRamkaWyszukiwania() {
        return kolorRamkaWyszukiwania;
    }
}
