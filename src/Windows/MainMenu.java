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
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == logo) {
            MainWindow.rightPanel.removeAll();
            MainWindow.rightPanel.setLayout(null);
            MainWindow.rightPanel.setBounds(WindowConstants.BORDER,0,WindowConstants.WIDTH - WindowConstants.BORDER,WindowConstants.HEIGHT);
            JLabel sty = new JLabel("To bedzie resetowalo prawy panel po kliknieciu w logo");
            sty.setBounds(10, 250, 350, 50);
            MainWindow.rightPanel.add(sty);
            MainWindow.rightPanel.repaint();
        } else if (source == repertuarButton) {
           MainWindow.rightPanel.removeAll();
            MainWindow.rightPanel.setLayout(null);
            MainWindow.rightPanel.setBounds(WindowConstants.BORDER,0,WindowConstants.WIDTH - WindowConstants.BORDER,WindowConstants.HEIGHT);
            JLabel sty = new JLabel("A to tez cos zrobi");
            sty.setBounds(10, 250, 200, 50);
            MainWindow.rightPanel.add(sty);
            MainWindow.rightPanel.repaint();
        }
    }
}

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
