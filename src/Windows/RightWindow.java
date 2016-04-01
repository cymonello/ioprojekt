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
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.JTableHeader;

/**
 * To jest klasa odpowiedzialna za poprawne wyświetlanie prawej strony naszego
 * porgramu Naraziewszelkie kolorki, obrazki są jedynie po to by nie było biało,
 * wszelkie schematy czcionek, kolorów itd dobierze się chyba już po 1
 * prototypie
 *
 * @author Bartłomiej
 */
public class RightWindow extends JPanel implements ActionListener {

    JTable jTable = new JTable();

    RightWindow() {
        StartWindow();
    }
    private ArrayList<Integer> lista = new ArrayList<>();

    public void StartWindow() {
        removeAll();
        setLayout(null);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);
        setBackground(Color.black);

        MoviesDB db = new MoviesDB();
        db.open();
        ArrayList<Object[]> listaRes = new ArrayList<>();
        lista.clear();
        Random rand = new Random();
        while (lista.size() < 3) {
            int ID = rand.nextInt(db.getMaxId()) + 1;
            if (lista.indexOf(ID) == -1) {
                lista.add(ID);
                listaRes.add(db.getMovieInfo(ID));
            }
        }
        db.close();

        for (int i = 0; i < 3; i++) {
            final int ID = lista.get(i);
            Object[] res = listaRes.get(i);
            JLabel labelIcon = new JLabel((ImageIcon) res[0]);
            labelIcon.setBounds(51 + (i * 250), 66, 198, 284);

            MouseListener aList = new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    JLabel label = (JLabel) e.getSource();
                    MainWindow.rightPanel.SchowInfoFilm(ID);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

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
            };

            labelIcon.addMouseListener(aList);
            add(labelIcon);
            JLabel opis = new JLabel((String) res[1]);
            opis.setBounds(26 + (i * 250), 360, 198 + 50, 40);
            opis.setForeground(Color.white);
            opis.setHorizontalAlignment(SwingConstants.CENTER);
            opis.setVerticalAlignment(SwingConstants.CENTER);
            add(opis);
            opis.addMouseListener(aList);
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

        ActionListener aList = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox source = (JComboBox) e.getSource();
                String str = (String) source.getSelectedItem();
                try {
                    final Repertoire rep = new Repertoire(str);
                    final String[][] tab = rep.getValue();

                    final String[] tab1 = {"Tytuł", "Wiek", "Info", "Język", "Czas", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};

                    jTable = new JTable(tab, tab1);
                    jTable.setEnabled(false);
                    JTableHeader header = jTable.getTableHeader();
                    header.setBackground(new Color(128, 17, 17));
                    header.setForeground(new Color(255, 227, 227));
                    jTable.setBackground(new Color(128, 17, 17));
                    jTable.setForeground(new Color(255, 227, 227));
                    header.setReorderingAllowed(false);
                    jTable.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int row = jTable.rowAtPoint(e.getPoint());
                            int col = jTable.columnAtPoint(e.getPoint());

                            if (col == 0) {
                                MainWindow.rightPanel.SchowInfoFilm(rep.getMovieID(row));
                            }
                            if (col >= 5 && col <= 16 && !" ".equals(tab[row][col])) {
                                MainWindow.rightPanel.MakeOrderPart1(rep.getTermID(row, col));
                            }
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

        };

        jcbDate.addActionListener(aList);
        add(jcbDate);
        aList.actionPerformed(new ActionEvent(jcbDate, 2, "ss"));
        repaint();
    }
    private JTextField tf;
    private final Vector<String> v = new Vector<>();
    private JComboBox jtfSearch = new JComboBox();
    private boolean hide_flag = false;

    void MakeSearch() throws SQLException, ClassNotFoundException {
        removeAll();
        setLayout(null);
        setBackground(Color.BLACK);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        Class.forName("com.mysql.jdbc.Driver");
        MoviesDB mdb = new MoviesDB();
        mdb.open();
        Integer i = mdb.getMaxId();
        String[] str = new String[i];
        for (int j = 0; j < i; j++) {
            str[j] = mdb.getTitle(j + 1);
        }
        mdb.close();
        jtfSearch.setEditable(true);
        //Przykładowe formatowanie wszukiwarki - narazie niedopracowane - OFF
        //jtfSearch.setModel(new DefaultComboBoxModel(str));
        //jtfSearch.setBounds(150, (int) (WindowConstants.HEIGHT * 0.3), WindowConstants.WIDTH - WindowConstants.BORDER - 300, 100);
        //jtfSearch.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 36));
        //jtfSearch.setForeground(Color.WHITE);
        //jtfSearch.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        //jtfSearch.setBackground(new Color(104, 158, 150));

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
        p.setBounds(150, (int) (WindowConstants.HEIGHT * 0.3), WindowConstants.WIDTH - WindowConstants.BORDER - 300, 100);

        add(p);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setPreferredSize(new Dimension(300, 150));

        ImageButton ibSearch;
        ibSearch = new ImageButton("res/Szukaj.png");
        ibSearch.setRolloverIcon(new ImageIcon("res/SzukajEntered.png"));
        ibSearch.setBounds(210, (int) (WindowConstants.HEIGHT * 0.3) + 130, WindowConstants.WIDTH - WindowConstants.BORDER - 420, 80);
        ibSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(jtfSearch.getEditor().getItem().toString());
            }

        });
        add(ibSearch);

        // usunięcie przycisku do rozwijania ComboBoxa w wyszukiwarce - narazie zakomentowane bo sie buguje przy 2-gim otworzeniu wyszukiwarki przy ciaglym otwarym oknie
        /*jtfSearch.setUI(new BasicComboBoxUI() { ///////////usuwa widoczne rozwijanie z JComboBox
            @Override                           ///////////Nie radze zmienaic miejsca wywołania setUI
            protected JButton createArrowButton() {
                return new JButton() {
                    @Override
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });*/
        //add(jtfSearch);
        //setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);
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

    /*
                0 - obrazek
                !1 - tytuł
                !2 - gatunek
                !3 - długość
                !4 - język 
                !5 - wiek
                !6 - reżyser
                !7 - aktorzy
                !8 - kraj
                !9 - rok prod
                10 - ocena
                11 - opis
     */
    void SchowInfoFilm(int ID) {
        removeAll();
        setLayout(null);
        setBackground(Color.BLACK);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        MoviesDB mdb = new MoviesDB();
        mdb.open();
        Object[] res = mdb.getMovieInfo(ID);
        mdb.close();
        JLabel labelIcon = new JLabel((ImageIcon) res[0]);
        labelIcon.setBounds(51, 66, 198, 284);
        add(labelIcon);

        JLabel[] infoName = new JLabel[9];
        infoName[0] = new JLabel("Tytuł:");
        infoName[1] = new JLabel("Gatunek:");
        infoName[2] = new JLabel("Długość:");
        infoName[3] = new JLabel("Język:");
        infoName[4] = new JLabel("Min wiek:");
        infoName[5] = new JLabel("Reżyseria:");
        infoName[6] = new JLabel("Obsada:");
        infoName[7] = new JLabel("Kraj:");
        infoName[8] = new JLabel("Rok produkcji:");
        JLabel[] infoFilm = new JLabel[9];
        for (int i = 0; i < infoName.length; i++) {
            infoName[i].setBounds(260, 66 + (i * 32), 110, 30);
            infoName[i].setForeground(new Color(247, 214, 185));
            infoName[i].setFont(new Font("Arial Black", Font.CENTER_BASELINE, 12));
            add(infoName[i]);

            infoFilm[i] = new JLabel(res[i + 1].toString());
            infoFilm[i].setBounds(380, 66 + (i * 32), 400, 30);
            infoFilm[i].setFont(new Font("Arial Black", Font.CENTER_BASELINE, 11));
            infoFilm[i].setForeground(Color.white);
            add(infoFilm[i]);
        }

        JTextArea filmDescription = new JTextArea(res[11].toString());

        filmDescription.setWrapStyleWord(true);
        filmDescription.setLineWrap(true);
        filmDescription.setEditable(false);
        filmDescription.setFocusable(false);
        //filmDescription.setOpaque(false);

        filmDescription.setEditable(false);
        filmDescription.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 11));
        filmDescription.setForeground(Color.white);
        filmDescription.setBounds(0, 0, 490, 150);
        filmDescription.setBackground(Color.black);
        filmDescription.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        JScrollPane scroll = new JScrollPane(filmDescription,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.setBounds(260, 354, 530, 210);
        scroll.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected void configureScrollBarColors() {
                this.trackColor = Color.black;
                thumbColor = new Color(84, 54, 54);

            }

            private JButton createZeroButton() { // 
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }
        });

        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        add(scroll, gbc_scrollPane);

        repaint();
    }

    void MakeBookingEdit() {
        removeAll();
        setLayout(null);
        setBackground(Color.BLACK);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        repaint();
    }

    void MakeInfoPage() {
        removeAll();
        setLayout(null);
        setBackground(Color.BLACK);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        repaint();
    }

    private void MakeOrderPart1(Integer ID) {

        removeAll();
        setLayout(null);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);
        setBackground(Color.black);
        System.out.println(ID);
        repaint();
    }
}
