/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;

import Repertoire.Repertoire;
import database.MoviesDB;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * To jest klasa odpowiedzialna za poprawne wyświetlanie prawej strony naszego
 * porgramu, reagowanie na naciśnięcie przycisków Sam do końca nie ogarniam co
 * ja to robię xD Nawet nie marnujcie czasu na zrozumienie tego :) Narazie
 * wszelkie kolorki, obrazki są jedynie po to by nie było biało, wszelkie
 * schematy czcionek, kolorów itd dobierze się chyba już po 1 prototypie
 *
 * @author Bartłomiej
 */
public class RightWindow extends JPanel implements ActionListener {
    JTable jTable = new JTable();

    RightWindow() {
        StartWindow();
    }

    public void StartWindow() {
        removeAll();
        setLayout(null);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);
        setBackground(Color.black);
        
        repaint();
    }

    public void MakeRepertoire() {
        JComboBox jcbDate;
        jTable = null;
        
        removeAll();
        setLayout(null);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);
        setBackground(Color.black);
        
        JLabel jlChooseDate = new JLabel("WYBIERZ DATE:");
        
        jlChooseDate.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 17));
        jlChooseDate.setBounds(10, 30, 180, 30);
        jlChooseDate.setForeground(Color.white);
        add(jlChooseDate);
        
        jcbDate = new JComboBox();
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yy");
        Calendar cal = Calendar.getInstance();
        jcbDate.addItem(ft.format(cal.getTime()));
        
        for (int i = 0; i < 5; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            java.util.Date data = cal.getTime();
            jcbDate.addItem(ft.format(cal.getTime()));
        }
        jcbDate.setBounds(200, 30, 120, 30);
        jcbDate.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 17));
        jcbDate.setForeground(Color.GRAY);
        jcbDate.setBackground(Color.CYAN);
       /* jcbDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JComboBox source = (JComboBox) e.getSource();
                //String str = (String) source.getSelectedItem();
                //System.out.println(str);
                //try {
                   // Repertoire rep = new Repertoire(str);
                    //String[][] tab = rep.getValue();
                    //String[] str1 = {"Tytuł","Wiek","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17"};
                    //jTable = new JTable(tab,str1);
                    //jTable.setBounds(10,100,980,490);
                    
                    //MainWindow.rightPanel.add(jTable);
                //} catch (ClassNotFoundException ex) {
                //} catch (SQLException ex){
               // }
            }

        });*/
        add(jcbDate);
        repaint();
    }

    public void MakeSearch() {
        removeAll();
        setLayout(null);
        setBackground(Color.BLACK);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

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
        add(ibSearch);

        add(jtfSearch);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

    }

}
