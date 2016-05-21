/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;

import java.awt.Color;

/**
 *
 * @author Bartłomiej
 */
public enum Colors {
    //PODSTAWOWY(Color.black, Color.white, Color.gray, Color.blue),
    //TESTOWY(Color.blue,Color.yellow,Color.white,Color.black),

    SCHEMAT1(new Color(61,255,97), //tlo
            new Color(178, 7,36), //nazwy
            new Color(147,88,255), //napisy
            new Color(28,204,160), //podswietlenie
            new Color(188,28,204), //tabelkaHeader
            new Color(98,153,139), //tabelkaTlo
            new Color(229,206,136), //tabelkaNapisy
            new Color(121, 182, 255), //datyTlo
            new Color(229,206,136), //datyNapisy
            new Color(28, 114, 5), //scroll
            new Color(28, 114, 5) //ramkaWyszkiwania
    ),

    SCHEMAT2(new Color(221,194,255), //tlo
            new Color(178, 7,36), //nazwy
            new Color(255,254,194), //napisy
            new Color(19, 80, 204), //podswietlenie
            new Color(55,49,64), //tabelkaHeader
            new Color(110, 103, 120), //tabelkaTlo
            new Color(229,206,136), //tabelkaNapisy
            new Color(110, 103, 120), //datyTlo
            new Color(229,206,136), //datyNapisy
            new Color(130, 61, 184), //scroll
            new Color(130, 61, 184) //ramkaWyszkiwania
    ),

    SCHEMAT3(new Color(205, 172, 164), //tlo
        new Color(58, 116, 83), //nazwy
        new Color(127,61,58), //napisy
        new Color(255, 255, 255), //podswietlenie
        new Color(85, 62, 57), //tabelkaHeader
        new Color(128, 97, 90), //tabelkaTlo
        new Color(255,255,255), //tabelkaNapisy
        new Color(85, 62, 57), //datyTlo
        new Color(179, 210, 255), //datyNapisy
        new Color(255, 0, 0), //scroll
        new Color(255, 83, 81) //ramkaWyszkiwania
    );


    private Color kolorTla;
    private Color kolorNapisow;
    private Color kolorNazw;
    private Color kolorPodswietlania;
    private Color kolorTabelkaHeader;
    private Color kolorTabelkaTlo;
    private Color kolorTabelkaNapisy;
    private Color kolorDatyTlo;
    private Color kolorDatyNapisy;
    private Color kolorScroll;
    private Color kolorRamkaWyszukiwania;
    Colors(Color tlo, Color nazwy, Color napisy, Color podswietlanie, Color tabelkaHeader, Color tabelkaTlo, Color tabelkaNapisy,
           Color datyTlo, Color datyNapisy, Color scroll, Color ramkaWyszukiwania) {
        kolorTla=tlo;
        kolorNapisow=napisy;
        kolorNazw=nazwy;
        kolorPodswietlania=podswietlanie;
        kolorTabelkaHeader = tabelkaHeader;
        kolorTabelkaTlo = tabelkaTlo;
        kolorTabelkaNapisy = tabelkaNapisy;
        kolorDatyTlo = datyTlo;
        kolorDatyNapisy = datyNapisy;
        kolorScroll = scroll;
        kolorRamkaWyszukiwania = ramkaWyszukiwania;
    }
    public Color getTlo(){
        return kolorTla;
    }
    public Color getPodswietlony(){
        return kolorPodswietlania;
    }
    public Color getNapisy(){
        return kolorNapisow;
    }
    public Color getNazwy(){
        return kolorNazw;
    }
    public Color getKolorTabelkaHeader() { return kolorTabelkaHeader; }
    public Color getKolorTabelka() { return kolorTabelkaTlo; }
    public Color getKolorTabelkaNapisy() { return kolorTabelkaNapisy; }
    public Color getKolorDatyNapisy() {
        return kolorDatyNapisy;
    }
    public Color getKolorDatyTlo() {
        return kolorDatyTlo;
    }
    public Color getKolorScroll() { return kolorScroll; }
    public Color getKolorRamkaWyszukiwania() { return kolorRamkaWyszukiwania; }
}
