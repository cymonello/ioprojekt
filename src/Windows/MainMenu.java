/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Klasa tworząca główne menu programu Klasa tworzy przyciski z obrazów, po
 * najechaniu na przycisk obrazek się zmienia imitując mrugnięcie guzika Jeśli
 * nie odnajdzie odpwiedniego obrazka w w katalogu res, zostanie stworzony
 * domyśly dla Javy przycisk Klikniecie na guzik powoduje zmiane wyświetlanej
 * zawartości na prawym panelu Przyciski niezależnie od systemu na którym
 * jesteśmy powinny wyglądać jednakowo
 *
 * @author Bartłomiej
 */
public class MainMenu extends JPanel implements ActionListener {

    private ImageButton logo;
    private ImageButton repertuarButton;
    private ImageButton wyszukajFilmButton;
    private ImageButton mojaRezerwacjaButton;
    private ImageButton informacjeButton;

    public MainMenu() {
        setLayout(null);

        logo = new ImageButton("res/KinoLogo.png");
        logo.setRolloverIcon(new ImageIcon("res/KinoLogoEntered.png"));
        logo.addActionListener(this);
        add(logo);

        repertuarButton = new ImageButton("res/Repertuar.png");
        repertuarButton.setRolloverIcon(new ImageIcon("res/RepertuarEntered.png"));
        repertuarButton.setBounds(0, 100, 200, 70);
        repertuarButton.addActionListener(this);
        add(repertuarButton);

        wyszukajFilmButton = new ImageButton("res/WyszukajFilm.png");
        wyszukajFilmButton.setRolloverIcon(new ImageIcon("res/WyszukajFilmEntered.png"));
        wyszukajFilmButton.setBounds(0, 170, 200, 70);
        wyszukajFilmButton.addActionListener(this);
        add(wyszukajFilmButton);

        mojaRezerwacjaButton = new ImageButton("res/MojaRezerwacja.png");
        mojaRezerwacjaButton.setRolloverIcon(new ImageIcon("res/MojaRezerwacjaEntered.png"));
        mojaRezerwacjaButton.setBounds(0, 240, 200, 70);
        mojaRezerwacjaButton.addActionListener(this);
        add(mojaRezerwacjaButton);

        informacjeButton = new ImageButton("res/Informacje.png");
        informacjeButton.setRolloverIcon(new ImageIcon("res/InformacjeEntered.png"));
        informacjeButton.setBounds(0, 310, 200, 70);
        informacjeButton.addActionListener(this);
        add(informacjeButton);

        JLabel cpr = new JLabel("© wszelkie prawa zastrzeżone :)");
        cpr.setForeground(Color.WHITE);
        cpr.setBounds(10, WindowConstants.HEIGHT - 60, 200, 30);
        add(cpr);
    }

    /**
     * Metoda actionPerformed zostaje wywołana gdy naciśniemu przycisk jako
     * parametr jej wywołania zostaje podane żródło (przycisk naciśnięty) Metoda
     * rozpoznaje który z naszych przycisków urzytkownik nacisnął oraz na jego
     * podstawie tworzy odpowiedni obiekt JPanel i wyświetla do po prawej
     * stronie okna
     *
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == logo) {
            MainWindow.rightPanel.StartWindow();
        } else if (source == repertuarButton) {
            MainWindow.rightPanel.MakeRepertoire();
        } else if (source == wyszukajFilmButton) {
            MainWindow.rightPanel.MakeSearch();
        } else if (source == logo) {
            MainWindow.rightPanel.StartWindow();
        }
    }
}

/**
 * Klasa pomocnicza dostępna tylko wpakiecie Windows Klasa umożliwiająca
 * tworzenie przycisków na podstawie obrazka W kontruktorze klasy podajemy
 * ściezkę dostępu do danego pliku Jeśli nie ma podanego pliku, przycisk bęzie
 * wyglądał jak klasyczny JButton w Javie
 *
 */
class ImageButton extends JButton {

    public ImageButton(String img) {
        this(new ImageIcon(img));
    }

    public ImageButton(ImageIcon icon) {
        setIcon(icon);
        setMargin(new Insets(0, 0, 0, 0));
        setIconTextGap(0);
        setBorderPainted(false);
        setBorder(null);
        setText(null);
        setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
    }

}

