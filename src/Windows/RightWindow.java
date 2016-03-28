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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

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

        jcbDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox source = (JComboBox) e.getSource();
                String str = (String) source.getSelectedItem();
                try {
                    Repertoire rep = new Repertoire(str);
                    String[][] tab = rep.getValue();

                    String[] tab1 = {"Tytuł", "Wiek", "??", "Język", "Czas", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};

                    jTable = new JTable(tab, tab1);
                    jTable.setEnabled(false);

                    JTableHeader header = jTable.getTableHeader();
                    header.setBackground(new Color(128, 17, 17));
                    header.setForeground(new Color(255, 227, 227));
                    jTable.setBackground(new Color(128, 17, 17));
                    jTable.setForeground(new Color(255, 227, 227));
                    jTable.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int row = jTable.rowAtPoint(e.getPoint());
                            int col = jTable.columnAtPoint(e.getPoint());
                            System.out.println(row + " " + col);
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                        }
                    });
                    for (int i = 0; i < jTable.getRowCount(); i++) {
                        jTable.setRowHeight(i, 15);
                    }
                    JScrollPane scrollPane = new JScrollPane(jTable);
                    scrollPane.setBounds(10, 150, 780, jTable.getRowCount() * jTable.getRowHeight() + 22);
                    MainWindow.rightPanel.add(scrollPane);
                    MainWindow.rightPanel.repaint();
                    //MakeRepertoireWithTab(rep.getValue());

                } catch (ClassNotFoundException | SQLException ex) {
                }
            }

        });
        add(jcbDate);
        repaint();
    }

    public void MakeSearch() throws SQLException, ClassNotFoundException {
        removeAll();
        setLayout(null);
        setBackground(Color.BLACK);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        JComboBox<String> jtfSearch = new JComboBox<>();
        Class.forName("com.mysql.jdbc.Driver");
        MoviesDB mdb = new MoviesDB();
        mdb.open();
        Integer i = 6;
        /*while(mdb.getTitle(i+1)!=null){
            i++;
        }*/
        String[] str = new String[i];
        for (int j = 0; j < i; j++) {
            str[j] = mdb.getTitle(j+1);
        }
        jtfSearch.setEnabled(true);
        jtfSearch.setModel(new DefaultComboBoxModel(str));
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
        mdb.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

    }

}
