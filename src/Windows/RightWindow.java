/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * To jest klasa odpowiedzialna za poprawne wyświetlanie prawej strony naszego
 * porgramu, reagowanie na naciśnięcie przycisków Sam do końca nie ogarniam co
 * ja to robię xD Nawet nie marnujcie czasu na zrozumienie tego :)
 * Narazie wszelkie kolorki, obrazki są jedynie po to by nie było biało, wszelkie schematy czcionek, kolorów itd dobierze się chyba już po 1 prototypie
 * @author Bartłomiej
 */
public class RightWindow extends JPanel implements ActionListener {

    RightWindow(int val) {
        switch (val) {
            case 1:
                MakeRepertoire(MainWindow.rightPanel);
                break;
            case 2:
                MakeSearch(MainWindow.rightPanel);
                break;
            case 3:
                break;
            default:
                StartWindow();
        }
    }

    private void StartWindow() {
    }

    private void MakeRepertoire(JPanel rp) {
        rp.removeAll();
        rp.setLayout(null);
        rp.setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        JLabel jlChooseDate = new JLabel("WYBIERZ DATE:");
        jlChooseDate.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 17));
        jlChooseDate.setBounds(10, 30, 180, 30);
        jlChooseDate.setForeground(Color.white);
        rp.add(jlChooseDate);
        JComboBox jcbDate = new JComboBox();
        SimpleDateFormat ft = new SimpleDateFormat("dd:MM:yy");
        Calendar cal = Calendar.getInstance();
        jcbDate.addItem(ft.format(cal.getTime()));
        for (int i = 0; i < 5; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            java.util.Date data = cal.getTime();
            jcbDate.addItem(ft.format(cal.getTime()));
            System.out.println(ft.format(data));
        }
        jcbDate.setBounds(200, 30, 120, 30);
        jcbDate.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 17));
        jcbDate.setForeground(Color.GRAY);
        jcbDate.setBackground(Color.CYAN);
        jcbDate.addActionListener(this);

        rp.add(jcbDate);
        JLabel sty = new JLabel("Tytul");
        sty.setBounds(10, 250, 350, 50);
        rp.add(sty);

        rp.repaint();
        rp.setBackground(new Color(104, 158, 205));
    }

    private void MakeSearch(JPanel rp) {
        rp.removeAll();
        rp.setLayout(null);
        rp.setBackground(Color.BLACK);
        rp.setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        JTextField jtfSearch = new JTextField("Szukaj filmu");
        jtfSearch.setBounds(150, (int) (WindowConstants.HEIGHT * 0.3), WindowConstants.WIDTH - WindowConstants.BORDER - 300, 100);
        jtfSearch.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 36));
        jtfSearch.setForeground(Color.WHITE);
        jtfSearch.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        jtfSearch.setBackground(new Color(104, 158, 150));

        ImageButton ibSearch;
        ibSearch = new ImageButton("res/Szukaj.png");
        ibSearch.setRolloverIcon(new ImageIcon("res/SzukajEntered.png"));
        ibSearch.setBounds(210, (int) (WindowConstants.HEIGHT * 0.3) + 130, WindowConstants.WIDTH - WindowConstants.BORDER - 420, 80);
        ibSearch.addActionListener(this);
        rp.add(ibSearch);

        rp.add(jtfSearch);
        rp.setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        rp.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
