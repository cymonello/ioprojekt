/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;

import Repertoire.Repertoire;
import database.MoviesDB;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;
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
    public ArrayList<Integer> lista = new ArrayList<>();
    public void StartWindow(){
        removeAll();
        setLayout(null);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);
        setBackground(Color.black);
        MoviesDB db = new MoviesDB();
        db.open();
        ArrayList<Object[]> listaRes = new ArrayList<>();
        lista.clear();
        Random rand = new Random();
        while(lista.size()<3){
            int ID = rand.nextInt(db.getMaxId())+1;
            if(lista.indexOf(ID)==-1){
                lista.add(ID);
                listaRes.add(db.getMovieInfo(ID));
            }
        }
        db.close();
        
        for(int i =0;i<3;i++){
            Object[] res = listaRes.get(i);
            JLabel labelIcon = new JLabel((ImageIcon)res[0]);
            labelIcon.setBounds(51+(i*250), 66, 198, 284);
            add(labelIcon);
            JLabel opis = new JLabel((String)res[1]);
            opis.setBounds(26+(i*250), 360, 198+50, 40);
            opis.setForeground(Color.white);
            opis.setHorizontalAlignment(SwingConstants.CENTER);
            opis.setVerticalAlignment(SwingConstants.CENTER);
            add(opis);
            opis.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel label = (JLabel) e.getSource();
                    MainWindow.rightPanel.SchowInfoFilm(label.getText());
                }

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {
                    JLabel label = (JLabel) e.getSource();
                    label.setForeground(Color.blue);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    JLabel label = (JLabel) e.getSource();
                    label.setForeground(Color.white);
                }
            });
        }
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
                            System.out.println(row + " " + col+ " "+ jTable.getColumnName(col)+ " " );
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
                    int hei = jTable.getRowCount() * jTable.getRowHeight() + 22;
                    if (hei > 400) {
                        hei = 400;
                    }
                    scrollPane.setBounds(10, 150, 780, hei);
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
    private JTextField tf;
    private final Vector<String> v = new Vector<>();
    private final JComboBox jtfSearch = new JComboBox();
    private boolean hide_flag = false;

    public void MakeSearch() throws SQLException, ClassNotFoundException {
        removeAll();
        setLayout(null);
        setBackground(Color.BLACK);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        Class.forName("com.mysql.jdbc.Driver");
        MoviesDB mdb = new MoviesDB();
        mdb.open();
        Integer i = mdb.getMaxId();
        /*while(mdb.getTitle(i+1)!=null){
            i++;
        }*/
        String[] str = new String[i];
        for (int j = 0; j < i; j++) {
            str[j] = mdb.getTitle(j + 1);
        }
        jtfSearch.setEditable(true);
        //jtfSearch.setModel(new DefaultComboBoxModel(str));
        jtfSearch.setBounds(150, (int) (WindowConstants.HEIGHT * 0.3), WindowConstants.WIDTH - WindowConstants.BORDER - 300, 100);
        //jtfSearch.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 36));
        //jtfSearch.setForeground(Color.WHITE);
        //jtfSearch.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        //jtfSearch.setBackground(new Color(104, 158, 150));
        jtfSearch.setUI(new BasicComboBoxUI() { ///////////usuwa widoczne rozwijanie z JComboBox
            @Override
            protected JButton createArrowButton() {
                return new JButton() {
                    @Override
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        tf = (JTextField) jtfSearch.getEditor().getEditorComponent();
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String text = tf.getText();
                        if (text.length() == 0) {
                            jtfSearch.hidePopup();
                            setModel(new DefaultComboBoxModel(v), "");
                        } else {
                            DefaultComboBoxModel m = getSuggestedModel(v, text);
                            if (m.getSize() == 0 || hide_flag) {
                                jtfSearch.hidePopup();
                                hide_flag = false;
                            } else {
                                setModel(m, text);
                                jtfSearch.showPopup();
                            }
                        }
                    }
                });
            }

            @Override
            public void keyPressed(KeyEvent e) {
                String text = tf.getText();
                int code = e.getKeyCode();
                switch (code) {
                    case KeyEvent.VK_ENTER:
                        hide_flag = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        hide_flag = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        for (int i = 0; i < v.size(); i++) {
                            String str = v.elementAt(i);
                            if (str.startsWith(text)) {
                                jtfSearch.setSelectedIndex(-1);
                                tf.setText(str);
                                return;
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        v.removeAllElements();
        for (String str1 : str) {
            v.addElement(str1);
        }
        setModel(new DefaultComboBoxModel(v), "");
        JPanel p = new JPanel(new BorderLayout());
        p.add(jtfSearch, null);
        add(p);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setPreferredSize(new Dimension(300, 150));

        ImageButton ibSearch;
        ibSearch = new ImageButton("res/Szukaj.png");
        ibSearch.setRolloverIcon(new ImageIcon("res/SzukajEntered.png"));
        ibSearch.setBounds(210, (int) (WindowConstants.HEIGHT * 0.3) + 130, WindowConstants.WIDTH - WindowConstants.BORDER - 420, 80);
        ibSearch.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(jtfSearch.getEditor().getItem().toString());
            }
            
        });
        add(ibSearch);

        add(jtfSearch);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        repaint();
        mdb.close();
    }

    private void setModel(DefaultComboBoxModel mdl, String str) {
        jtfSearch.setModel(mdl);
        jtfSearch.setSelectedIndex(-1);
        tf.setText(str);
    }

    private static DefaultComboBoxModel getSuggestedModel(java.util.List<String> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for (String s : list) {
            if (s.toLowerCase().contains(text.toLowerCase())) {
                m.addElement(s);
            }
        }
        return m;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

    }

    private void SchowInfoFilm(String source) {
        System.out.println("Tu bedzą informacje o "+ source);
    }
}
