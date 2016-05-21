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
    PODSTAWOWY(Color.black, Color.white, Color.gray, Color.blue),
    TESTOWY(Color.blue,Color.yellow,Color.white,Color.black);

    private Color kolorTla;
    private Color kolorNapisow;
    private Color kolorNazw;
    private Color kolorPodswietlania;
    Colors(Color tlo, Color nazwy, Color napisy, Color podswietlanie) {
        kolorTla=tlo;
        kolorNapisow=napisy;
        kolorNazw=nazwy;
        kolorPodswietlania=podswietlanie;
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
}
