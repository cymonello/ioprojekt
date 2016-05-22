/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
        setBackground(WindowConstants.schematKolorow.getTlo());
        logo = new ImageButton("res" + File.separator + "KinoLogo.png");
        logo.setRolloverIcon(new ImageIcon("res" + File.separator + "KinoLogoEntered.png"));
        logo.addActionListener(this);
        add(logo);

        repertuarButton = new ImageButton("res" + File.separator + "Repertuar.png");
        repertuarButton.setRolloverIcon(new ImageIcon("res" + File.separator + "RepertuarEntered.png"));
        repertuarButton.setBounds(0, 100, 200, 70);
        repertuarButton.addActionListener(this);
        add(repertuarButton);

        wyszukajFilmButton = new ImageButton("res" + File.separator + "WyszukajFilm.png");
        wyszukajFilmButton.setRolloverIcon(new ImageIcon("res" + File.separator + "WyszukajFilmEntered.png"));
        wyszukajFilmButton.setBounds(0, 170, 200, 70);
        wyszukajFilmButton.addActionListener(this);
        add(wyszukajFilmButton);

        mojaRezerwacjaButton = new ImageButton("res" + File.separator + "MojaRezerwacja.png");
        mojaRezerwacjaButton.setRolloverIcon(new ImageIcon("res" + File.separator + "MojaRezerwacjaEntered.png"));
        mojaRezerwacjaButton.setBounds(0, 240, 200, 70);
        mojaRezerwacjaButton.addActionListener(this);
        add(mojaRezerwacjaButton);

        informacjeButton = new ImageButton("res" + File.separator + "Informacje.png");
        informacjeButton.setRolloverIcon(new ImageIcon("res" + File.separator + "InformacjeEntered.png"));
        informacjeButton.setBounds(0, 310, 200, 70);
        informacjeButton.addActionListener(this);
        add(informacjeButton);

        JLabel cpr = new JLabel("© wszelkie prawa zastrzeżone :)");
        cpr.setForeground(Color.WHITE);
        cpr.setBounds(10, WindowConstants.HEIGHT - 60, 200, 30);
        add(cpr);
        ImageButton schematPodstawowy = new ImageButton("res" + File.separator + "test.png");
        schematPodstawowy.setBounds(5, 390, 40, 40);
        schematPodstawowy.setOpaque(false);
        schematPodstawowy.setBorderPainted(false);
        schematPodstawowy.setContentAreaFilled(false);
        add(schematPodstawowy);
        schematPodstawowy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowConstants.schematKolorow = Colors.SCHEMAT0;
                MainWindow.leftPanel.setBackground(WindowConstants.schematKolorow.getTlo());
                MainWindow.rightPanel.StartWindow();
                MainWindow.rightPanel.StartWindow();
            }
        });
        ImageButton schemat1 = new ImageButton("res" + File.separator + "test.png");
        schemat1.setBounds(55, 390, 40, 40);
        schemat1.setOpaque(false);
        schemat1.setBorderPainted(false);
        schemat1.setContentAreaFilled(false);
        add(schemat1);
        schemat1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowConstants.schematKolorow = Colors.SCHEMAT1;
                MainWindow.leftPanel.setBackground(WindowConstants.schematKolorow.getTlo());
                MainWindow.rightPanel.StartWindow();
                MainWindow.rightPanel.StartWindow();
            }
        });
        ImageButton schemat2 = new ImageButton("res" + File.separator + "test.png");
        schemat2.setBounds(105, 390, 40, 40);
        schemat2.setOpaque(false);
        schemat2.setBorderPainted(false);
        schemat2.setContentAreaFilled(false);
        add(schemat2);
        schemat2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowConstants.schematKolorow = Colors.SCHEMAT2;
                MainWindow.leftPanel.setBackground(WindowConstants.schematKolorow.getTlo());
                MainWindow.rightPanel.StartWindow();
                MainWindow.rightPanel.StartWindow();
            }
        });
        ImageButton schemat3 = new ImageButton("res" + File.separator + "test.png");
        schemat3.setBounds(155, 390, 40, 40);
        schemat3.setOpaque(false);
        schemat3.setBorderPainted(false);
        schemat3.setContentAreaFilled(false);
        add(schemat3);
        schemat3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowConstants.schematKolorow = Colors.SCHEMAT3;
                MainWindow.leftPanel.setBackground(WindowConstants.schematKolorow.getTlo());
                MainWindow.rightPanel.StartWindow();
                MainWindow.rightPanel.StartWindow();
            }
        });
    }

    /**
     * Metoda actionPerformed zostaje wywołana gdy naciśniemu przycisk jako
     * parametr jej wywołania zostaje podane żródło (przycisk naciśnięty) Metoda
     * rozpoznaje który z naszych przycisków urzytkownik nacisnął oraz na jego
     * podstawie tworzy odpowiedni obiekt JPanel i wyświetla do po prawej
     * stronie okna
     *
     * @param e
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
        } else if (source == mojaRezerwacjaButton) {
            MainWindow.rightPanel.MakeBookingEdit();
        } else if (source == informacjeButton) {
            MainWindow.rightPanel.MakeInfoPage();
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

    private String str;

    public ImageButton(String img) {
        this(new ImageIcon(img));
        str = img;
    }

    public ImageButton(ImageIcon icon) {
        setIcon(icon);
        setMargin(new Insets(0, 0, 0, 0));
        setIconTextGap(0);
        setBorderPainted(false);
        setOpaque(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setBorder(null);
        setText(null);
        setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
    }

    public void setIcon(String str) {
        this.str = str;
        setIcon(new ImageIcon(str));
    }

    @Override
    public boolean equals(Object obj) {
        return str.equals(obj);
    }

}
