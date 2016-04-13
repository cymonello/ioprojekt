/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;

import Booking.Booking;
import Booking.Hall;
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
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
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
                    MainWindow.rightPanel.ShowInfoFilm(ID);
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
                    jTable.getColumnModel().getColumn(0).setPreferredWidth(400);
                    jTable.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int row = jTable.rowAtPoint(e.getPoint());
                            int col = jTable.columnAtPoint(e.getPoint());

                            if (col == 0) {
                                MainWindow.rightPanel.ShowInfoFilm(rep.getMovieID(row));
                            }
                            if (col >= 5 && col <= 16 && !" ".equals(tab[row][col])) {
                                Booking b = new Booking();
                                b.startBooking(rep.getTermID(row, col));
                                MainWindow.rightPanel.MakeOrderPart1(b);
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
    void ShowInfoFilm(int ID) {
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

    private void MakeOrderPart1(final Booking booking) {

        removeAll();
        setLayout(null);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);
        setBackground(Color.black);

        String[] info = booking.getInfo();
        JLabel title = new JLabel(info[0]);
        title.setForeground(new Color(247, 214, 185));
        title.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(50, 70, 700, 50);
        add(title);

        JLabel date = new JLabel(info[1] + " : " + info[2]);
        date.setForeground(new Color(247, 214, 185));
        date.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 15));
        date.setHorizontalAlignment(SwingConstants.CENTER);
        date.setBounds(50, 120, 700, 50);
        add(date);

        JLabel[] ticket = new JLabel[4];
        ticket[0] = new JLabel("Normalny");
        ticket[1] = new JLabel("Szkolny");
        ticket[2] = new JLabel("Seniorski");
        ticket[3] = new JLabel("Studencki");

        for (int i = 0; i < 4; i++) {
            ticket[i].setBounds(200, 190 + i * 50, 180, 50);
            ticket[i].setHorizontalAlignment(SwingConstants.RIGHT);
            add(ticket[i]);
            ticket[i].setForeground(new Color(247, 214, 185));
            ticket[i].setFont(new Font("Arial Black", Font.CENTER_BASELINE, 12));

        }

        String[] tab = {"0", "1", "2", "3", "4", "5"};

        final JComboBox[] jcbTicketNumber = new JComboBox[4];
        for (int i = 0; i < 4; i++) {
            jcbTicketNumber[i] = new JComboBox(tab);
            jcbTicketNumber[i].setBounds(400, 200 + i * 50, 50, 30);
            add(jcbTicketNumber[i]);
            jcbTicketNumber[i].setSelectedIndex(0);

        }

        ImageButton next = new ImageButton("res/dalej.png");
        next.setRolloverIcon(new ImageIcon("res/DalejEntered.png"));
        next.setBounds(350, 400, 100, 40);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ile = 0;
                for (int i = 0; i < 4; i++) {
                    if (jcbTicketNumber[i].getSelectedItem() != "0") {
                        int item = Integer.parseInt((String) jcbTicketNumber[i].getSelectedItem());
                        for (int j = 0; j < item; j++) {
                            booking.newTicket(i);
                            ile++;
                        }
                    }
                }
                if (ile > 0) {
                    MainWindow.rightPanel.MakeOrderPart2(booking);
                }
            }
        });
        add(next);

        repaint();
    }

    private void MakeOrderPart2(final Booking booking) {
        removeAll();
        setLayout(null);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        String[] info = booking.getInfo();
        JLabel title = new JLabel(info[0]);
        title.setForeground(new Color(247, 214, 185));
        title.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(50, 20, 700, 50);
        add(title);

        JLabel date = new JLabel(info[1] + " : " + info[2]);
        date.setForeground(new Color(247, 214, 185));
        date.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 15));
        date.setHorizontalAlignment(SwingConstants.CENTER);
        date.setBounds(50, 50, 700, 50);
        add(date);

        final Hall hal = new Hall(info[1], info[2], Integer.parseInt(booking.getInfo()[3]));
        final int[][] sal = hal.getHall();

        final ArrayList<int[]> list = new ArrayList<>();
        JLabel ekran = new JLabel(new ImageIcon("res/Ekran.png"));
        ekran.setBounds(160, 110, 480, 28);
        add(ekran);
        for (int i = 0; i < sal.length; i++) {
            for (int j = 0; j < sal[i].length; j++) {
                final int k = i;
                final int l = j;
                if (sal[i][j] == 0) {
                    final ImageButton sala = new ImageButton("res/miejsceDostepne.png");
                    sala.setRolloverIcon(new ImageIcon("res/miejsceWybrane.png"));
                    sala.setBounds(100 + 30 * j, 150 + 30 * i, 28, 28);
                    add(sala);
                    sala.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (sala.equals("res/miejsceDostepne.png")) {
                                if (list.size() < booking.listLength()) {
                                    sala.setIcon("res/miejsceWybrane.png");
                                    int[] tmp = {k, l};
                                    list.add(tmp);
                                }
                            } else {
                                sala.setIcon("res/miejsceDostepne.png");
                                sala.setRolloverIcon(new ImageIcon("res/miejsceWybrane.png"));
                                for (int m = 0; m < list.size(); m++) {
                                    if (list.get(m)[0] == k && list.get(m)[1] == l) {
                                        list.remove(m);
                                    }
                                }
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
                } else if (sal[i][j] == 1) {
                    ImageButton sala = new ImageButton("res/miejsceZajete.png");
                    sala.setBounds(100 + 30 * j, 150 + 30 * i, 28, 28);
                    add(sala);
                }
            }
        }
        ImageButton miejsceDostepne = new ImageButton("res/miejsceDostepne.png");
        ImageButton miejsceWybrane = new ImageButton("res/miejsceWybrane.png");
        ImageButton miejsceZajete = new ImageButton("res/miejsceZajete.png");
        miejsceDostepne.setBounds(100, 480, 28, 28);
        miejsceWybrane.setBounds(300, 480, 28, 28);
        miejsceZajete.setBounds(500, 480, 28, 28);
        add(miejsceWybrane);
        add(miejsceZajete);
        add(miejsceDostepne);

        JLabel zajete = new JLabel("Miejsce niedostępne");
        JLabel wolne = new JLabel("Miejsce dostępne");
        JLabel wybrane = new JLabel("Miejsce wyrane");
        wolne.setBounds(130, 480, 270, 28);
        wolne.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 12));
        wolne.setHorizontalAlignment(SwingConstants.LEFT);
        wolne.setForeground(new Color(247, 214, 185));
        wybrane.setBounds(330, 480, 270, 28);
        wybrane.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 12));
        wybrane.setHorizontalAlignment(SwingConstants.LEFT);
        wybrane.setForeground(new Color(247, 214, 185));
        zajete.setBounds(530, 480, 270, 28);
        zajete.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 12));
        zajete.setHorizontalAlignment(SwingConstants.LEFT);
        zajete.setForeground(new Color(247, 214, 185));
        add(wybrane);
        add(zajete);
        add(wolne);

        ImageButton next = new ImageButton("res/Dalej.png");
        next.setRolloverIcon(new ImageIcon("res/DalejEntered.png"));
        next.setBounds(350, 510, 100, 40);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.size() < booking.listLength()) {
                    JOptionPane.showMessageDialog(null, "Proszę wybrać następującą liczbę miejsc: " + booking.listLength(), "Error", INFORMATION_MESSAGE);
                } else {
                    booking.getSeat(list);
                    MainWindow.rightPanel.MakeOrderPart3(booking);
                }
            }
        });
        add(next);
        repaint();
    }

    private void MakeOrderPart3(final Booking booking) {
        removeAll();
        setLayout(null);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        String[] info = booking.getInfo();
        JLabel title = new JLabel(info[0]);
        title.setForeground(new Color(247, 214, 185));
        title.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(50, 70, 700, 50);
        add(title);

        JLabel date = new JLabel(info[1] + " : " + info[2]);
        date.setForeground(new Color(247, 214, 185));
        date.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 15));
        date.setHorizontalAlignment(SwingConstants.CENTER);
        date.setBounds(50, 100, 700, 50);
        add(date);

        JLabel jlDane = new JLabel("Proszę podać następujące dane:");
        jlDane.setForeground(new Color(247, 214, 185));
        jlDane.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 15));
        jlDane.setHorizontalAlignment(SwingConstants.CENTER);
        jlDane.setBounds(50, 140, 700, 50);
        add(jlDane);
        JLabel[] dane = new JLabel[4];

        dane[0] = new JLabel("Imie");
        dane[1] = new JLabel("Nazwisko");
        dane[2] = new JLabel("Adres e-mail");
        dane[3] = new JLabel("Numer telefonu");

        for (int i = 0; i < dane.length; i++) {
            dane[i].setForeground(new Color(247, 214, 185));
            dane[i].setFont(new Font("Arial Black", Font.CENTER_BASELINE, 15));
            dane[i].setHorizontalAlignment(SwingConstants.RIGHT);
            dane[i].setBounds(50, 190 + 50 * i, 340, 50);
            add(dane[i]);
        }
        final JTextField[] jtfPol = new JTextField[4];
        for (int i = 0; i < jtfPol.length; i++) {
            jtfPol[i] = new JTextField();
            jtfPol[i].setBounds(410, 200 + 50 * i, 120, 30);
            add(jtfPol[i]);
        }

        ImageButton next = new ImageButton("res/Dalej.png");
        next.setRolloverIcon(new ImageIcon("res/DalejEntered.png"));
        next.setBounds(350, 510, 100, 40);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean correct = true;
                String name = jtfPol[0].getText();
                String replaceAll1 = name.replaceAll("[a-z]+", "").replaceAll("[A-Z]+", "").replaceAll(" ", "");

                String lastname = jtfPol[1].getText();
                String replaceAll2 = lastname.replaceAll("[a-z]+", "").replaceAll("[A-Z]+", "").replaceAll(" ", "");

                String email = jtfPol[2].getText();
                String replaceAll3 = email.replaceAll(" ", "");

                if (replaceAll1.length() > 0 || name.length() == 0
                        || replaceAll2.length() > 0 || lastname.length() == 0
                        || !email.equals(replaceAll3) || email.indexOf('@') <= 0 || email.indexOf('@') > email.indexOf('.', email.indexOf('@'))) {
                    correct = false;
                }

                int tel_num = 0;
                try {
                    if (!jtfPol[3].getText().replaceAll(" ", "").equals(jtfPol[3].getText()) || jtfPol[3].getText().length() != 9) {
                        correct = false;
                    }
                    tel_num = Integer.parseInt(jtfPol[3].getText().replaceAll(" ", ""));
                    if (tel_num <= 0) {
                        correct = false;
                    }
                } catch (Exception exc) {
                    correct = false;
                }
                if (!correct) {
                    JOptionPane.showMessageDialog(null, "Proszę wpisać poprawne dane", "Error", INFORMATION_MESSAGE);

                } else {
                    MainWindow.rightPanel.MakeOrderPart4(booking, name, lastname, email, tel_num);
                }
            }
        });
        add(next);
        repaint();
    }

    private void MakeOrderPart4(final Booking booking, final String name, final String lastname, final String email, final int number) {
        removeAll();
        setLayout(null);
        setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);

        JLabel check = new JLabel("Sprawdź poprawność danych do rezerwacji");
        check.setBounds(50, 60, 700, 50);
        check.setForeground(new Color(247, 214, 185));
        check.setFont(new Font("Arial Black", Font.CENTER_BASELINE, 20));
        check.setHorizontalAlignment(SwingConstants.CENTER);
        add(check);

        JLabel[] infoName = new JLabel[8];
        infoName[0] = new JLabel("Tytuł:");
        infoName[1] = new JLabel("Data:");
        infoName[2] = new JLabel("Godzina:");
        infoName[3] = new JLabel("Ilość miejsc:");
        infoName[4] = new JLabel("Imie:");
        infoName[5] = new JLabel("Nazwisko:");
        infoName[6] = new JLabel("Adres e-mail:");
        infoName[7] = new JLabel("Numer telefonu:");

        for (int i = 0; i < infoName.length; i++) {
            infoName[i].setBounds(50, 120 + 40 * i, 250, 40);
            infoName[i].setForeground(new Color(247, 214, 185));
            infoName[i].setFont(new Font("Arial Black", Font.CENTER_BASELINE, 16));
            infoName[i].setHorizontalAlignment(SwingConstants.RIGHT);
            add(infoName[i]);
        }
        ImageButton wstecz = new ImageButton("res/Wstecz.png");
        wstecz.setBounds(WIDTH, WIDTH, WIDTH, HEIGHT);
        wstecz.setBounds(290, 450, 100, 40);
        wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow.rightPanel.MakeOrderPart3(booking);
            }
        });
        add(wstecz);
        ImageButton next = new ImageButton("res/Dalej.png");
        next.setRolloverIcon(new ImageIcon("res/DalejEntered.png"));
        next.setBounds(410, 450, 100, 40);

        JLabel[] info = new JLabel[8];
        info[0] = new JLabel(booking.getInfo()[0]);
        info[1] = new JLabel(booking.getInfo()[1]);
        info[2] = new JLabel(booking.getInfo()[2]);
        info[3] = new JLabel(Integer.toString(booking.listLength()));
        info[4] = new JLabel(name);
        info[5] = new JLabel(lastname);
        info[6] = new JLabel(email);
        info[7] = new JLabel(Integer.toString(number));

        for (int i = 0; i < info.length; i++) {
            info[i].setBounds(310, 120 + 40 * i, 390, 40);
            info[i].setForeground(new Color(247, 214, 185));
            info[i].setFont(new Font("Arial Black", Font.CENTER_BASELINE, 16));
            info[i].setHorizontalAlignment(SwingConstants.LEFT);
            add(info[i]);
        }
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                booking.endBooking(name, lastname, email, number);
                JOptionPane.showMessageDialog(null, "Proces rezerwacji przebiegł poprawnie", "Udana rezerwacja", INFORMATION_MESSAGE);
                MainWindow.rightPanel.StartWindow();
            }
        });
        add(next);
        repaint();
    }
}
