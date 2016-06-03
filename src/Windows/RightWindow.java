package Windows;

import Booking.Booking;
import Booking.Hall;
import Repertoire.Repertoire;
import Search.Dates;
import Search.Search;
import database.MoviesDB;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import static Windows.WindowConstants.*;
import database.OrdersDB;
import database.TicketsDB;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.JTableHeader;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * Klasa odpowiedzialna za poprawne wyświetlanie prawej strony programu. Do
 * ustalania szczegółowego wyglądu panelu korzysta z klasy WindowConstants.
 *
 * @author Bartłomiej
 */
public class RightWindow extends JPanel {
    
    private JTable jTable = new JTable();

    /**
     * Konstruktor odpowiedzialny za stworzenie panelu, domyślnie ustawia w nim
     * okno startowe, 3 wybrane, proponowane filmy
     */
    public RightWindow() {
        StartWindow();
    }
    private ArrayList<Integer> lista = new ArrayList<>();

    /**
     * Funkcj uruchamiana przy starcie aplikacji powoduje wyświetlenie się 3
     * losowy wybranych filmów po kliknięciu można przjesc do mapnelu ze
     * szczegółowymi informacjami o filmie
     */
    final public void StartWindow() {
        removeAll();
        setLayout(null);
        setBounds(BORDER, 0, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT);
        setBackground(WindowConstants.schematKolorow.getTlo());
        
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
                    MainWindow.rightPanel.ShowInfoFilm(ID, MainWindow.rightPanel);
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
                    label.setForeground(WindowConstants.schematKolorow.getPodswietlony());
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    JLabel label = (JLabel) e.getSource();
                    label.setForeground(WindowConstants.schematKolorow.getNapisy());
                }
            };
            
            labelIcon.addMouseListener(aList);
            add(labelIcon);
            JLabel opis = new JLabel((String) res[1]);
            opis.setBounds(26 + (i * 250), 360, 198 + 50, 40);
            opis.setForeground(WindowConstants.schematKolorow.getNapisy());
            opis.setHorizontalAlignment(SwingConstants.CENTER);
            opis.setVerticalAlignment(SwingConstants.CENTER);
            add(opis);
            opis.addMouseListener(aList);
        }
        repaint();
    }

    /**
     * Ten string służy do zapamiętania jaka data przy wyborze w repertuarze
     * została wybrana jako ostatnia gdzieki temu po wygenerowaniu nowego
     * repertuaru mamy zaznaczony poprawny dzien, na kóry wskazuje repertuar
     */
    private String dataa;

    /**
     * Funkcja tworzy panel z filmamy dostępnymi na wybrany dzień Istnieje
     * możliwość wybnoru jednego z 5 kolejnych dni (łącznie z dniem dzisiejszym
     * 6 dni do wyboru). W wyświetlonej tabeli z repertuarem urzytkownik może
     * kliknąć na dowolną komórkę w pierwszej kolumnie w celu uzyskania
     * informacji o dlanym filmie. Istnije możliwość klikania w komórki w
     * kolumnach odpowiedzialnych za godziny w których można zobaczyć dany film,
     * po kliknięciu w godzinę zostanie wyświtlone okno rezerwacji biletów na
     * dany film o konkretnej godzinie danego dnia
     */
    public void MakeRepertoire() {
        JComboBox jcbDate;
        dataa = null;
        removeAll();
        setLayout(null);
        setBounds(BORDER, 0, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT);
        setBackground(WindowConstants.schematKolorow.getTlo());
        
        JLabel jlChooseDate = new JLabel("WYBIERZ DATE:");
        
        jlChooseDate.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 17));
        jlChooseDate.setBounds(10, 30, 180, 30);
        jlChooseDate.setForeground(WindowConstants.schematKolorow.getNazwy());
        add(jlChooseDate);
        
        jcbDate = new JComboBox();
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yy");
        Calendar cal = Calendar.getInstance();
        jcbDate.addItem(ft.format(cal.getTime()));
        
        for (int i = 0; i < 5; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            jcbDate.addItem(ft.format(cal.getTime()));
        }
        jcbDate.setBounds(200, 30, 120, 30);
        jcbDate.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 17));
        jcbDate.setForeground(Color.GRAY);
        jcbDate.setBackground(Color.CYAN);
        
        ActionListener aList = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                JComboBox source = (JComboBox) e.getSource();
                dataa = source.getSelectedItem().toString();
                String str = (String) source.getSelectedItem();
                try {
                    
                    final Repertoire rep = new Repertoire(str);
                    final String[][] tab = rep.getValue();
                    if (tab.length > 0) {
                        final String[] tab1 = {"Tytuł", "Wiek", "Info", "Język", "Czas", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"};
                        
                        jTable = new JTable(tab, tab1);
                        jTable.setGridColor(WindowConstants.schematKolorow.getTlo());
                        jTable.setEnabled(false);
                        JTableHeader header = jTable.getTableHeader();
                        UIManager.getDefaults().put("TableHeader.cellBorder", BorderFactory.createLineBorder(WindowConstants.schematKolorow.getTlo()));
                        
                        header.setBackground(Color.yellow);
                        header.setBackground(WindowConstants.schematKolorow.getKolorTabelkaHeader());
                        header.setForeground(WindowConstants.schematKolorow.getKolorTabelkaNapisy());
                        jTable.setBackground(WindowConstants.schematKolorow.getKolorTabelka());
                        jTable.setForeground(WindowConstants.schematKolorow.getKolorTabelkaNapisy());
                        header.setReorderingAllowed(false);
                        jTable.getColumnModel().getColumn(0).setPreferredWidth(400);
                        jTable.addMouseListener(new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                int row = jTable.rowAtPoint(e.getPoint());
                                int col = jTable.columnAtPoint(e.getPoint());
                                
                                if (col == 0) {
                                    MainWindow.rightPanel.ShowInfoFilm(rep.getMovieID(row), MainWindow.rightPanel);
                                }
                                if (col >= 5 && col <= 17 && !" ".equals(tab[row][col])) {
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
                        MainWindow.rightPanel.removeAll();
                        JLabel jlChooseDate = new JLabel("WYBIERZ DATĘ:");
                        
                        jlChooseDate.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 17));
                        jlChooseDate.setBounds(10, 30, 180, 30);
                        jlChooseDate.setForeground(WindowConstants.schematKolorow.getNapisy());
                        add(jlChooseDate);
                        
                        JComboBox jcbDate1 = new JComboBox();
                        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yy");
                        Calendar cal = Calendar.getInstance();
                        jcbDate1.addItem(ft.format(cal.getTime()));
                        
                        for (int i = 0; i < 5; i++) {
                            cal.add(Calendar.DAY_OF_MONTH, 1);
                            jcbDate1.addItem(ft.format(cal.getTime()));
                        }
                        jcbDate1.setBounds(200, 30, 120, 30);
                        jcbDate1.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 17));
                        jcbDate1.setForeground(WindowConstants.schematKolorow.getKolorDatyNapisy());
                        jcbDate1.setBackground(WindowConstants.schematKolorow.getKolorDatyTlo());
                        for (int i = 0; i < jcbDate1.getItemCount(); i++) {
                            if (jcbDate1.getItemAt(i).toString().equals(dataa)) {
                                jcbDate1.setSelectedIndex(i);
                            }
                        }
                        add(jcbDate1);
                        jcbDate1.addActionListener(this);
                        JScrollPane scrollPane = new JScrollPane(jTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        
                        scrollPane.setBorder(BorderFactory.createLineBorder(WindowConstants.schematKolorow.getTlo(), 1));
                        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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
                                this.trackColor = WindowConstants.schematKolorow.getTlo();
                                thumbColor = WindowConstants.schematKolorow.getKolorScroll();
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
                        
                        int hei = jTable.getRowCount() * jTable.getRowHeight() + 22;
                        if (hei > 400) {
                            hei = 400;
                        }
                        scrollPane.getViewport().setBackground(WindowConstants.schematKolorow.getTlo());
                        scrollPane.setBounds(10, 150, 780, hei);
                        MainWindow.rightPanel.add(scrollPane);
                        MainWindow.rightPanel.repaint();
                    } else {
                        JLabel brak = new JLabel("Brak repertuaru w wybranym dniu");
                        brak.setBounds(50, 150, 700, 50);
                        brak.setForeground(WindowConstants.schematKolorow.getNazwy());
                        brak.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 25));
                        brak.setHorizontalAlignment(SwingConstants.CENTER);
                        add(brak);
                    }
                    
                } catch (ClassNotFoundException | SQLException ex) {
                }
            }
            
        };
        
        jcbDate.addActionListener(aList);
        add(jcbDate);
        aList.actionPerformed(new ActionEvent(jcbDate, 2, "ss"));
        repaint();
    }

    /**
     * zmienne niezbędne do poprawnego funkcjinowania wyszukiwarki
     */
    private JTextField tf;
    private final Vector<String> v = new Vector<>();
    private final JComboBox jtfSearch = new JComboBox() {
        @Override
        public void updateUI() {
            super.updateUI();
            UIManager.put("ComboBox.squareButton", Boolean.FALSE);
            setUI(new BasicComboBoxUI() {
                @Override
                protected JButton createArrowButton() {
                    JButton b = new JButton();
                    b.setBorder(BorderFactory.createEmptyBorder());
                    b.setVisible(false);
                    return b;
                }
            });
            setBorder(BorderFactory.createLineBorder(WindowConstants.schematKolorow.getKolorRamkaWyszukiwania()));
        }
    };
    private boolean hide_flag = false;

    /**
     * Metoda odpowiedzialna za wyświatlanie poprawnie wyszukiwarki.
     * Wyszukiwarka działa w sposób dyanmiczny, po każdorazowej zmianie w polu
     * tekstowym wyświetla się lista pasujących tytułów do zapytania możliwe
     * jest naciśnięcie strzałki w duł aby wybrac filmy lub klawisza Enter
     */
    public void MakeSearch() {
        try {
            removeAll();
            setLayout(null);
            setBackground(WindowConstants.schematKolorow.getTlo());
            setBounds(BORDER, 0, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT);
            JLabel wyszukiwarka = new JLabel("Wyszukaj film");
            add(wyszukiwarka);
            wyszukiwarka.setBounds(50, 75, 700, 100);
            wyszukiwarka.setForeground(WindowConstants.schematKolorow.getNazwy());
            wyszukiwarka.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 25));
            wyszukiwarka.setHorizontalAlignment(SwingConstants.CENTER);
            Class.forName("com.mysql.jdbc.Driver");
            MoviesDB mdb = new MoviesDB();
            mdb.open();
            Integer i = mdb.getMaxId();
            String[] str = new String[i];
            for (int j = 0; j < i; j++) {
                str[j] = mdb.getTitle(j + 1);
            }
            mdb.close();
            
            jtfSearch.setBorder(BorderFactory.createLineBorder(WindowConstants.schematKolorow.getKolorRamkaWyszukiwania(), 3));
            
            jtfSearch.setEditable(true);
            Object popup = jtfSearch.getUI().getAccessibleChild(jtfSearch, 0);
            Component c = ((Container) popup).getComponent(0);
            if (c instanceof JScrollPane) {
                JScrollPane scrollpane = (JScrollPane) c;
                scrollpane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
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
                        this.trackColor = WindowConstants.schematKolorow.getTlo();
                        thumbColor = WindowConstants.schematKolorow.getKolorScroll();
                    }
                    
                    private JButton createZeroButton() {
                        JButton jbutton = new JButton();
                        jbutton.setPreferredSize(new Dimension(0, 0));
                        jbutton.setMinimumSize(new Dimension(0, 0));
                        jbutton.setMaximumSize(new Dimension(0, 0));
                        return jbutton;
                    }
                });
                
            }
            
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
                            Search sear = new Search();
                            sear.open();
                            final Integer[] idTab = sear.byTitle(jtfSearch.getEditor().getItem().toString());
                            sear.close();
                            makeFound(idTab);
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
            p.setBounds(250, 250, 300, 50);
            
            add(p);
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            setPreferredSize(new Dimension(300, 150));
            
            ImageButton ibSearch;
            ibSearch = new ImageButton("res" + File.separator + "Szukaj.png");
            ibSearch.setRolloverIcon(new ImageIcon("res" + File.separator + "SzukajEntered.png"));
            ibSearch.setBounds(210, (int) (WindowConstants.HEIGHT * 0.3) + 130, WindowConstants.WIDTH - BORDER - 420, 80);
            ibSearch.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Search sear = new Search();
                    sear.open();
                    final Integer[] idTab = sear.byTitle(jtfSearch.getEditor().getItem().toString());
                    sear.close();
                    makeFound(idTab);
                }
                
            });
            add(ibSearch);
            
            repaint();
            mdb.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RightWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Funkcja pomocnicza do wyszukiwarki
     *
     * @param mdl
     * @param str
     */
    private void setModel(DefaultComboBoxModel mdl, String str) {
        jtfSearch.setModel(mdl);
        jtfSearch.setSelectedIndex(-1);
        tf.setText(str);
    }

    /**
     * Funkcja pomocniczna do wyszukiwarki
     *
     * @param list
     * @param text
     * @return DefaultComboBoxModel
     */
    private static DefaultComboBoxModel getSuggestedModel(java.util.List<String> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        
        for (String s : list) {
            if (s.toLowerCase().contains(text.toLowerCase())) {
                m.addElement(s);
            }
        }
        return m;
    }

    /**
     * Panel wyświtlający informacje o filmie, informacje te pobiera
     * bezpośrednio z bazy danych na podstawie ID filmu
     *
     * @param ID - ID filmu o którym wyświetlane mają być informacje
     * @param panel - panel w oknie programu w którym mają zostać umieszczone
     * iformacje o filmie
     */
    public void ShowInfoFilm(final int ID, JPanel panel) {
        panel.removeAll();
        panel.setLayout(null);
        panel.setBackground(WindowConstants.schematKolorow.getTlo());
        
        MoviesDB mdb = new MoviesDB();
        mdb.open();
        Object[] res = mdb.getMovieInfo(ID);
        mdb.close();
        JLabel labelIcon = new JLabel((ImageIcon) res[0]);
        labelIcon.setBounds(51, 16, 198, 284);
        panel.add(labelIcon);
        
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
        final JLabel[] infoFilm = new JLabel[9];
        for (int i = 0; i < infoName.length; i++) {
            infoName[i].setBounds(260, 16 + (i * 32), 110, 30);
            infoName[i].setForeground(WindowConstants.schematKolorow.getNazwy());
            infoName[i].setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 12));
            panel.add(infoName[i]);
            
            infoFilm[i] = new JLabel(res[i + 1].toString());
            infoFilm[i].setBounds(380, 16 + (i * 32), 400, 30);
            infoFilm[i].setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 11));
            infoFilm[i].setForeground(WindowConstants.schematKolorow.getNapisy());
            panel.add(infoFilm[i]);
        }
        infoFilm[1].addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Search sear = new Search();
                sear.open();
                final Integer[] idTab = sear.byGenre(infoFilm[1].getText());
                sear.close();
                makeFound(idTab);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                infoFilm[1].setForeground(WindowConstants.schematKolorow.getPodswietlony());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                infoFilm[1].setForeground(WindowConstants.schematKolorow.getNapisy());
            }
        });
        
        MoviesDB db = new MoviesDB();
        db.open();
        double ocena = db.getNote(ID);
        db.close();
        for (int i = 0; i < (int) ocena; i++) {
            ImageButton ilegwiazdek = new ImageButton("res" + File.separator + "gwiazdka.png");
            panel.add(ilegwiazdek);
            ilegwiazdek.setBounds(51 + i * 30, 310, 30, 30);
        }
        DecimalFormat df = new DecimalFormat("#.00");
        JLabel ocen = new JLabel(df.format(ocena));
        ocen.setBounds(61 + (int) ocena * 30, 310, 200, 30);
        if (!(ocena > 0)) {
            ocen.setText("Brak ocen");
            ocen.setBounds(50, 310, 200, 30);
        }
        ocen.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 20));
        ocen.setForeground(WindowConstants.schematKolorow.getNazwy());
        panel.add(ocen);
        
        JLabel ocenfilm = new JLabel("Oceń film");
        ocenfilm.setBounds(50, 350, 200, 30);
        ocenfilm.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 12));
        ocenfilm.setForeground(WindowConstants.schematKolorow.getNazwy());
        panel.add(ocenfilm);
        final ImageButton[] gwiazdki = new ImageButton[5];
        for (int i = 0; i < gwiazdki.length; i++) {
            final int m = i + 1;
            gwiazdki[i] = new ImageButton("res" + File.separator + "/gwiazdkaSzara.png");
            panel.add(gwiazdki[i]);
            gwiazdki[i].setBounds(51 + i * 30, 380, 30, 30);
            gwiazdki[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (int j = 0; j < gwiazdki.length; j++) {
                        for (int l = 0; l < gwiazdki[j].getMouseListeners().length; l++) {
                            gwiazdki[j].removeMouseListener(gwiazdki[j].getMouseListeners()[l]);
                        }
                    }
                    for (int j = 0; j < gwiazdki.length; j++) {
                        for (int l = 0; l < gwiazdki[j].getMouseListeners().length; l++) {
                            gwiazdki[j].removeMouseListener(gwiazdki[j].getMouseListeners()[l]);
                        }
                    }
                    for (int j = 0; j < m; j++) {
                        gwiazdki[j].setIcon("res" + File.separator + "gwiazdka.png");
                    }
                    MoviesDB db = new MoviesDB();
                    db.open();
                    db.addNote(ID, m);
                    db.close();
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (gwiazdki[m - 1].getMouseListeners().length > 0) {
                        for (int j = 0; j < m; j++) {
                            gwiazdki[j].setIcon("res" + File.separator + "gwiazdka.png");
                        }
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    if (gwiazdki[m - 1].getMouseListeners().length > 0) {
                        for (int j = 0; j < m; j++) {
                            gwiazdki[j].setIcon("res/gwiazdkaSzara.png");
                        }
                    }
                }
            });
        }
        ImageButton terms = new ImageButton("res" + File.separator + "sprawdz.png");
        terms.setRolloverIcon(new ImageIcon("res" + File.separator + "sprawdzEntered.png"));
        panel.add(terms);
        terms.setBounds(50, 420, 150, 30);
        terms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow.rightPanel.Terms(ID);
            }
        });
        JTextArea filmDescription = new JTextArea(res[11].toString());
        
        filmDescription.setWrapStyleWord(true);
        filmDescription.setLineWrap(true);
        filmDescription.setEditable(false);
        filmDescription.setFocusable(false);
        
        filmDescription.setEditable(false);
        filmDescription.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 11));
        filmDescription.setForeground(WindowConstants.schematKolorow.getNapisy());
        filmDescription.setBounds(0, 0, 490, 150);
        filmDescription.setBackground(WindowConstants.schematKolorow.getTlo());
        filmDescription.setBorder(BorderFactory.createLineBorder(WindowConstants.schematKolorow.getTlo(), 1));
        
        JScrollPane scroll = new JScrollPane(filmDescription,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.setBounds(260, 304, 530, 210);
        scroll.setBorder(BorderFactory.createLineBorder(WindowConstants.schematKolorow.getTlo(), 1));
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
                this.trackColor = WindowConstants.schematKolorow.getTlo();
                thumbColor = WindowConstants.schematKolorow.getKolorScroll();
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
        panel.add(scroll, gbc_scrollPane);
        panel.repaint();
    }

    /**
     * Metoda wyświetlająca panel odpwiedzialny za edycję rezerwacji, w panelu
     * tym urzytkownik podaje numer rezerwacji oraz hasło, aby móc edytować
     * rezerwację dane muszą być poprawne, muszą zostać zaakceptowane przez
     * system
     */
    public void MakeBookingEdit() {
        removeAll();
        setLayout(null);
        setBackground(WindowConstants.schematKolorow.getTlo());
        setBounds(BORDER, 0, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT);
        JLabel jlNumer = new JLabel("Numer rezerwacji:");
        jlNumer.setBounds(100, 100, 250, 50);
        jlNumer.setForeground(WindowConstants.schematKolorow.getNazwy());
        jlNumer.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 20));
        jlNumer.setHorizontalAlignment(SwingConstants.RIGHT);
        add(jlNumer);
        JLabel jlHaslo = new JLabel("Hasło:");
        jlHaslo.setBounds(100, 150, 250, 50);
        jlHaslo.setForeground(WindowConstants.schematKolorow.getNazwy());
        jlHaslo.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 20));
        jlHaslo.setHorizontalAlignment(SwingConstants.RIGHT);
        add(jlHaslo);
        final JTextField jtfNumer = new JTextField();
        jtfNumer.setBounds(370, 105, 150, 40);
        add(jtfNumer);
        
        final JPasswordField jtfPassw = new JPasswordField();
        jtfPassw.setBounds(370, 155, 150, 40);
        add(jtfPassw);
        ImageButton next = new ImageButton("res" + File.separator + "Dalej.png");
        next.setRolloverIcon(new ImageIcon("res" + File.separator + "DalejEntered.png"));
        next.setBounds(310, 220, 100, 40);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isOk = false;
                OrdersDB odb = new OrdersDB();
                odb.open();
                try {
                    isOk = odb.check(Integer.parseInt(jtfNumer.getText()), new String(jtfPassw.getPassword()));
                } catch (NumberFormatException nfe) {
                    isOk = false;
                } finally {
                    odb.close();
                }
                if (isOk) {
                    MainWindow.rightPanel.editPart2(Integer.parseInt(jtfNumer.getText()));
                } else {
                    JOptionPane.showMessageDialog(null, "Niepoprawne dane rezerwacji", "Błąd", INFORMATION_MESSAGE);
                }
            }
        });
        add(next);
        repaint();
    }

    /**
     * Wyświetla dalsze infromacje związane z edycją rezerwacji.
     */
    private void editPart2(final int numer) {
        removeAll();
        setLayout(null);
        setBackground(WindowConstants.schematKolorow.getTlo());
        setBounds(BORDER, 0, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT);
        JLabel[] co = new JLabel[4];
        co[0] = new JLabel("Data:");
        co[1] = new JLabel("Godzina:");
        co[2] = new JLabel("Numer sali:");
        co[3] = new JLabel("Ilosc miejsc:");
        for (int i = 0; i < co.length; i++) {
            co[i].setBounds(140, 120 + 30 * i, 250, 30);
            co[i].setForeground(WindowConstants.schematKolorow.getNazwy());
            co[i].setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 16));
            co[i].setHorizontalAlignment(SwingConstants.RIGHT);
            add(co[i]);
        }
        TicketsDB tdb = new TicketsDB();
        tdb.open();
        final Object[] informacje = tdb.orderToEdit(numer);
        final JLabel[] infor = new JLabel[4];
        for (int i = 0; i < infor.length; i++) {
            infor[i] = new JLabel((String) informacje[i]);
            infor[i].setBounds(400, 120 + 30 * i, 250, 30);
            infor[i].setForeground(WindowConstants.schematKolorow.getNazwy());
            infor[i].setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 16));
            infor[i].setHorizontalAlignment(SwingConstants.LEFT);
            add(infor[i]);
        }
        final int[][] sala = (int[][]) informacje[4];
        tdb.close();
        
        ImageButton zmienMiejsce = new ImageButton("res" + File.separator + "zmienMiejsca.png");
        zmienMiejsce.setRolloverIcon(new ImageIcon("res" + File.separator + "zmienMiejscaEntered.png"));
        zmienMiejsce.setBounds(200, 300, 200, 23);
        zmienMiejsce.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zmienMiejsca(numer, sala, Integer.parseInt(informacje[3].toString()));
            }
            
        });
        add(zmienMiejsce);
        ImageButton usunRezerwacje = new ImageButton("res" + File.separator + "usunRez.png");
        usunRezerwacje.setRolloverIcon(new ImageIcon("res" + File.separator + "usunRezEntered.png"));
        usunRezerwacje.setBounds(400, 300, 200, 23);
        usunRezerwacje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = new String[2];
                options[0] = "TAK";
                options[1] = "NIE";
                int wynik = JOptionPane.showOptionDialog(null, "Czy napewno chcesz usunąć swoją rezerwację?", "Jesteś pewien?", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
                if (wynik == JOptionPane.YES_OPTION) {
                    TicketsDB tdb = new TicketsDB();
                    tdb.open();
                    tdb.deleteOrder(numer);
                    tdb.close();
                    MainWindow.rightPanel.StartWindow();
                }
            }
            
        });
        add(usunRezerwacje);
        repaint();
    }
    
    private void zmienMiejsca(final int numer, final int[][] salaTab, final int ilemiejsc) {
        removeAll();
        setLayout(null);
        setBounds(BORDER, 0, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT);
        final ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < ilemiejsc; i++) {
            list.add(1);
        }
        JLabel ekran = new JLabel(new ImageIcon("res" + File.separator + "Ekran.png"));
        ekran.setBounds(160, 110, 480, 28);
        add(ekran);
        for (int i = 0; i < salaTab.length; i++) {
            for (int j = 0; j < salaTab[i].length; j++) {
                final int k = i;
                final int l = j;
                switch (salaTab[i][j]) {
                    case 0: {
                        final ImageButton sala = new ImageButton("res" + File.separator + "miejsceDostepne.png");
                        sala.setRolloverIcon(new ImageIcon("res" + File.separator + "miejsceWybrane.png"));
                        sala.setBounds(100 + 30 * j, 150 + 30 * i, 28, 28);
                        add(sala);
                        sala.addMouseListener(new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (sala.equals("res" + File.separator + "miejsceDostepne.png")) {
                                    if (list.size() < ilemiejsc) {
                                        sala.setIcon("res" + File.separator + "miejsceWybrane.png");
                                        list.add(1);
                                        salaTab[k][l] = 2;
                                    }
                                } else {
                                    sala.setIcon("res" + File.separator + "miejsceDostepne.png");
                                    sala.setRolloverIcon(new ImageIcon("res" + File.separator + "miejsceWybrane.png"));
                                    list.remove(0);
                                    salaTab[k][l] = 0;
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
                        break;
                    }
                    case 1: {
                        ImageButton sala = new ImageButton("res" + File.separator + "miejsceZajete.png");
                        sala.setBounds(100 + 30 * j, 150 + 30 * i, 28, 28);
                        add(sala);
                        break;
                    }
                    case 2: {
                        final ImageButton sala = new ImageButton("res" + File.separator + "miejsceWybrane.png");
                        sala.setRolloverIcon(new ImageIcon("res" + File.separator + "miejsceWybrane.png"));
                        sala.setBounds(100 + 30 * j, 150 + 30 * i, 28, 28);
                        add(sala);
                        sala.addMouseListener(new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (sala.equals("res" + File.separator + "miejsceWybrane.png")) {
                                    sala.setIcon("res" + File.separator + "miejsceDostepne.png");
                                    list.remove(0);
                                    salaTab[k][l] = 0;
                                    
                                } else if (list.size() < ilemiejsc) {
                                    sala.setIcon("res" + File.separator + "miejsceWybrane.png");
                                    salaTab[k][l] = 2;
                                    list.add(1);
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
                        break;
                    }
                    default:
                        break;
                }
            }
        }
        ImageButton miejsceDostepne = new ImageButton("res" + File.separator + "miejsceDostepne.png");
        ImageButton miejsceWybrane = new ImageButton("res" + File.separator + "miejsceWybrane.png");
        ImageButton miejsceZajete = new ImageButton("res" + File.separator + "miejsceZajete.png");
        miejsceDostepne.setBounds(100, 480, 28, 28);
        miejsceWybrane.setBounds(300, 480, 28, 28);
        miejsceZajete.setBounds(500, 480, 28, 28);
        add(miejsceWybrane);
        add(miejsceZajete);
        add(miejsceDostepne);
        
        JLabel zajete = new JLabel("Miejsce niedostępne");
        JLabel wolne = new JLabel("Miejsce dostępne");
        JLabel wybrane = new JLabel("Miejsce wybrane");
        wolne.setBounds(130, 480, 270, 28);
        wolne.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 12));
        wolne.setHorizontalAlignment(SwingConstants.LEFT);
        wolne.setForeground(WindowConstants.schematKolorow.getNapisy());
        wybrane.setBounds(330, 480, 270, 28);
        wybrane.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 12));
        wybrane.setHorizontalAlignment(SwingConstants.LEFT);
        wybrane.setForeground(WindowConstants.schematKolorow.getNapisy());
        zajete.setBounds(530, 480, 270, 28);
        zajete.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 12));
        zajete.setHorizontalAlignment(SwingConstants.LEFT);
        zajete.setForeground(WindowConstants.schematKolorow.getNapisy());
        add(wybrane);
        add(zajete);
        add(wolne);
        
        ImageButton next = new ImageButton("res" + File.separator + "Dalej.png");
        next.setRolloverIcon(new ImageIcon("res" + File.separator + "DalejEntered.png"));
        next.setBounds(350, 510, 100, 40);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.size() < ilemiejsc) {
                    JOptionPane.showMessageDialog(null, "Proszę wybrać następującą liczbę miejsc: " + ilemiejsc, "Error", INFORMATION_MESSAGE);
                } else {
                    TicketsDB tdb = new TicketsDB();
                    tdb.open();
                    boolean isOK = tdb.saveEdited(numer, salaTab);
                    tdb.close();
                    if (isOK) {
                        MainWindow.rightPanel.editPart2(numer);
                    } else {
                        JOptionPane.showMessageDialog(null, "Ktoś już zarezerwował wybrane miejsca, wybierz inne.", "Error", INFORMATION_MESSAGE);
                    }
                }
            }
        });
        add(next);
        repaint();
    }

    /**
     * Funkcja wyświtla informacje związane z działaniem aplikacji oraz z kinenm
     *
     */
    public void MakeInfoPage() {
        removeAll();
        setLayout(null);
        setBackground(WindowConstants.schematKolorow.getTlo());
        setBounds(BORDER, 0, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT);
        JTextPane opis1 = new JTextPane();
        opis1.setText("\t\tZa pomocą aplikacji możesz wybrać seans, który Cię interesuje, a następnie dokonać rezerwacji. Oferujemy szeroką gamę filmów, zatem każdy znajdzie dla siebie coś ciekawego. W zakładce repertuar znajduję się rozpiska godzinowa filmów na 6 kolejnych dni. Po wybraniu godziny z tabeli możesz dokonać rezerwacji. Aplikacja naszego kina jest również obszerną bazą najpotrzebniejszych informacji niezbędnych do podjęcia decyzji o wyborze filmu. Dzięki systemowi ocen możesz zobaczyć, jak film został oceniony przez społeczność odwiedzającą aplikację naszego kina. Również z poziomu informacji o filmie istnieje możliwość dokonania rezerwacji, o ile film jest w danym czasie grany.");
        
        SimpleAttributeSet sa = new SimpleAttributeSet();
        StyleConstants.setAlignment(sa, StyleConstants.ALIGN_JUSTIFIED);
        
        opis1.getStyledDocument().setParagraphAttributes(0, 0, sa, false);
        opis1.setEditable(false);
        opis1.setFocusable(false);
        
        opis1.setEditable(false);
        opis1.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 14));
        opis1.setForeground(WindowConstants.schematKolorow.getNapisy());
        opis1.setBounds(50, 100, 350, 275);
        opis1.setBackground(WindowConstants.schematKolorow.getTlo());
        opis1.setBorder(BorderFactory.createLineBorder(WindowConstants.schematKolorow.getTlo(), 1));
        add(opis1);
        
        JTextPane opis2 = new JTextPane();
        opis2.setText("\t\tOferujemy wysokich standardów sale kinowe, dzięki czemu oglądanie u nas filmów jest czystą przyjemnością. Nasze kino przystosowane jest do seansów 3D, przed wejściem na taki pokaz każdy z klientów dostaje okulary umożliwiające mu pełne cieszenie się z filmów w formacie 3D. W budynku kina znajduję się również sklepik, w którym możecie dokonać zakupu produktów spożywczych niezbędnych do osiągnięcia pełnego zadowolenia z seansów. W razie jakichkolwiek pytań piszcie na adres: (wklej tu adres kina xD).");
        opis2.getStyledDocument().setParagraphAttributes(0, 0, sa, false);
        opis2.setEditable(false);
        opis2.setFocusable(false);
        
        opis2.setEditable(false);
        opis2.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 14));
        opis2.setForeground(WindowConstants.schematKolorow.getNapisy());
        opis2.setBounds(50, 373, 700, 260);
        opis2.setBackground(WindowConstants.schematKolorow.getTlo());
        opis2.setBorder(BorderFactory.createLineBorder(WindowConstants.schematKolorow.getTlo(), 1));
        add(opis2);
        JLabel labelIcon = new JLabel(new ImageIcon("res" + File.separator + "kino.png"));
        labelIcon.setBounds(400, 105, 342, 270);
        add(labelIcon);
        repaint();
    }

    /**
     * Metoda odpowiedzialna za pierwszy etap rezerwacji Na tym etapie dokonuje
     * sie wyboru ilości biletów i rodzaju biletów W przypadku braku
     * jakiegokolwiek wyboru wyświtlany zostaje komunikat, nie można przejść do
     * kolejnego etapu rezerwacji
     *
     * @param booking - obiekt rezerwacji który w czasie porzechodzenia
     * kolejnych etapów zbiera informacje na temat rezerwacji, poprawnym
     * zakonczeniu rezerwuje miejsca w bazie danych
     */
    private void MakeOrderPart1(final Booking booking) {
        
        removeAll();
        setLayout(null);
        setBounds(BORDER, 0, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT);
        setBackground(WindowConstants.schematKolorow.getTlo());
        
        String[] info = booking.getInfo();
        JLabel title = new JLabel(info[0]);
        title.setForeground(WindowConstants.schematKolorow.getNazwy());
        title.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(50, 70, 700, 50);
        add(title);
        
        JLabel date = new JLabel(info[1] + " : " + info[2]);
        date.setForeground(WindowConstants.schematKolorow.getNazwy());
        date.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 15));
        date.setHorizontalAlignment(SwingConstants.CENTER);
        date.setBounds(50, 120, 700, 50);
        add(date);
        double[] ceny = booking.ceny();
        JLabel[] ticket = new JLabel[4];
        DecimalFormat df = new DecimalFormat("#.00");
        ticket[0] = new JLabel("Normalny " + df.format(ceny[0]) + " zł");
        ticket[1] = new JLabel("Szkolny " + df.format(ceny[1]) + " zł");
        ticket[2] = new JLabel("Seniorski " + df.format(ceny[2]) + " zł");
        ticket[3] = new JLabel("Studencki " + df.format(ceny[3]) + " zł");
        
        for (int i = 0; i < 4; i++) {
            ticket[i].setBounds(200, 190 + i * 50, 180, 50);
            ticket[i].setHorizontalAlignment(SwingConstants.RIGHT);
            add(ticket[i]);
            ticket[i].setForeground(WindowConstants.schematKolorow.getNazwy());
            ticket[i].setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 12));
            
        }
        
        String[] tab = {"0", "1", "2", "3", "4", "5"};
        
        final JComboBox[] jcbTicketNumber = new JComboBox[4];
        for (int i = 0; i < 4; i++) {
            jcbTicketNumber[i] = new JComboBox(tab) {
                @Override
                public void updateUI() {
                    super.updateUI();
                    UIManager.put("ComboBox.squareButton", Boolean.FALSE);
                    setUI(new BasicComboBoxUI() {
                        @Override
                        protected JButton createArrowButton() {
                            JButton b = new JButton();
                            b.setBorder(BorderFactory.createEmptyBorder());
                            b.setBackground(WindowConstants.schematKolorow.getKolorScroll());
                            b.setVisible(true);
                            return b;
                        }
                    });
                    setBorder(BorderFactory.createLineBorder(WindowConstants.schematKolorow.getKolorRamkaWyszukiwania()));
                }
            };
            jcbTicketNumber[i].setBounds(400, 200 + i * 50, 50, 30);
            add(jcbTicketNumber[i]);
            jcbTicketNumber[i].setSelectedIndex(0);
            
        }
        
        ImageButton next = new ImageButton("res" + File.separator + "dalej.png");
        next.setRolloverIcon(new ImageIcon("res" + File.separator + "DalejEntered.png"));
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
                } else {
                    JOptionPane.showMessageDialog(null, "Nie wybrano biletów", "Błąd", INFORMATION_MESSAGE);
                    
                }
            }
        });
        add(next);
        
        repaint();
    }

    /**
     * Kolejny etap rezerwacji W tym etapie wyberamy miejsca na stali zgodnie z
     * ilością biletów wybranych wczesniej Do momozentu naciśnięcia przycisku
     * "Dalej" istnieje możliwość zmienienia wybranych miejsc Na mapie sali
     * pokazywane są miejsca które można wybrać, są już zajęte lub wybraliśmy je
     * właśnie
     *
     * @param booking - obiekt rezerwacji powstały w poprzednim kroku
     */
    private void MakeOrderPart2(final Booking booking) {
        removeAll();
        setLayout(null);
        setBounds(BORDER, 0, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT);
        
        String[] info = booking.getInfo();
        JLabel title = new JLabel(info[0]);
        title.setForeground(WindowConstants.schematKolorow.getNazwy());
        title.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(50, 20, 700, 50);
        add(title);
        
        JLabel date = new JLabel(info[1] + " : " + info[2]);
        date.setForeground(WindowConstants.schematKolorow.getNazwy());
        date.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 15));
        date.setHorizontalAlignment(SwingConstants.CENTER);
        date.setBounds(50, 50, 700, 50);
        add(date);
        
        final Hall hal = new Hall(info[1], info[2], Integer.parseInt(booking.getInfo()[3]));
        final int[][] sal = hal.getHall();
        
        final ArrayList<int[]> list = new ArrayList<>();
        JLabel ekran = new JLabel(new ImageIcon("res" + File.separator + "Ekran.png"));
        ekran.setBounds(160, 110, 480, 28);
        add(ekran);
        int ileMiejscWolnych = 0;
        for (int i = 0; i < sal.length; i++) {
            for (int j = 0; j < sal[i].length; j++) {
                final int k = i;
                final int l = j;
                if (sal[i][j] == 0) {
                    ileMiejscWolnych++;
                    final ImageButton sala = new ImageButton("res" + File.separator + "miejsceDostepne.png");
                    sala.setRolloverIcon(new ImageIcon("res" + File.separator + "miejsceWybrane.png"));
                    sala.setBounds(100 + 30 * j, 150 + 30 * i, 28, 28);
                    add(sala);
                    sala.addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (sala.equals("res" + File.separator + "miejsceDostepne.png")) {
                                if (list.size() < booking.listLength()) {
                                    sala.setIcon("res" + File.separator + "miejsceWybrane.png");
                                    int[] tmp = {k, l};
                                    list.add(tmp);
                                }
                            } else {
                                sala.setIcon("res" + File.separator + "miejsceDostepne.png");
                                sala.setRolloverIcon(new ImageIcon("res" + File.separator + "miejsceWybrane.png"));
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
                    ImageButton sala = new ImageButton("res" + File.separator + "miejsceZajete.png");
                    sala.setBounds(100 + 30 * j, 150 + 30 * i, 28, 28);
                    add(sala);
                }
            }
        }
        ImageButton miejsceDostepne = new ImageButton("res" + File.separator + "miejsceDostepne.png");
        ImageButton miejsceWybrane = new ImageButton("res" + File.separator + "miejsceWybrane.png");
        ImageButton miejsceZajete = new ImageButton("res" + File.separator + "miejsceZajete.png");
        miejsceDostepne.setBounds(100, 480, 28, 28);
        miejsceWybrane.setBounds(300, 480, 28, 28);
        miejsceZajete.setBounds(500, 480, 28, 28);
        add(miejsceWybrane);
        add(miejsceZajete);
        add(miejsceDostepne);
        
        JLabel zajete = new JLabel("Miejsce niedostępne");
        JLabel wolne = new JLabel("Miejsce dostępne");
        JLabel wybrane = new JLabel("Miejsce wybrane");
        wolne.setBounds(130, 480, 270, 28);
        wolne.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 12));
        wolne.setHorizontalAlignment(SwingConstants.LEFT);
        wolne.setForeground(WindowConstants.schematKolorow.getNapisy());
        wybrane.setBounds(330, 480, 270, 28);
        wybrane.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 12));
        wybrane.setHorizontalAlignment(SwingConstants.LEFT);
        wybrane.setForeground(WindowConstants.schematKolorow.getNapisy());
        zajete.setBounds(530, 480, 270, 28);
        zajete.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 12));
        zajete.setHorizontalAlignment(SwingConstants.LEFT);
        zajete.setForeground(WindowConstants.schematKolorow.getNapisy());
        add(wybrane);
        add(zajete);
        add(wolne);
        
        ImageButton next = new ImageButton("res" + File.separator + "Dalej.png");
        next.setRolloverIcon(new ImageIcon("res" + File.separator + "DalejEntered.png"));
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
        if (ileMiejscWolnych < booking.listLength()) {
            JOptionPane.showMessageDialog(null, "Niewystarczająca ilość wolnych miejsc, dostępne: " + ileMiejscWolnych, "Błąd", javax.swing.JOptionPane.OK_OPTION);
            MainWindow.rightPanel.StartWindow();
        }
    }

    /**
     * W tym etapie rezerwacji podaje się dane niezbędne do końcowego
     * zatwierdzenia rezerwacji W przypadku nie poprawnych danych zostanie
     * wyświtlony komunikat Imię, nazwisko - może zawierać jedynie znaki [a-z]
     * oraz [A-Z] adres e-mail - musi zawierać znak @ oraz kropke "." które
     * występuje dalej niż "@" numer telefonu - musi się składać z 9 cyfer
     *
     * @param booking - obiekt rezerwacji powstały w poprzednich krokach
     */
    private void MakeOrderPart3(final Booking booking) {
        removeAll();
        setLayout(null);
        setBounds(BORDER, 0, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT);
        
        String[] info = booking.getInfo();
        JLabel title = new JLabel(info[0]);
        title.setForeground(WindowConstants.schematKolorow.getNazwy());
        title.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(50, 70, 700, 50);
        add(title);
        
        JLabel date = new JLabel(info[1] + " : " + info[2]);
        date.setForeground(WindowConstants.schematKolorow.getNazwy());
        date.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 15));
        date.setHorizontalAlignment(SwingConstants.CENTER);
        date.setBounds(50, 100, 700, 50);
        add(date);
        
        JLabel jlDane = new JLabel("Proszę podać następujące dane:");
        jlDane.setForeground(WindowConstants.schematKolorow.getNazwy());
        jlDane.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 15));
        jlDane.setHorizontalAlignment(SwingConstants.CENTER);
        jlDane.setBounds(50, 140, 700, 50);
        add(jlDane);
        JLabel[] dane = new JLabel[4];
        
        dane[0] = new JLabel("Imie");
        dane[1] = new JLabel("Nazwisko");
        dane[2] = new JLabel("Adres e-mail");
        dane[3] = new JLabel("Numer telefonu");
        
        for (int i = 0; i < dane.length; i++) {
            dane[i].setForeground(WindowConstants.schematKolorow.getNazwy());
            dane[i].setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 15));
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
        jtfPol[0].setToolTipText("Wprowadź swoje imię");
        jtfPol[1].setToolTipText("Wprowadź swoje nazwisko");
        jtfPol[2].setToolTipText("Wprowadź poprawny adres e-mail, na który zostanie wysłane potwierdzenie");
        jtfPol[3].setToolTipText("Wprowadź numer telefonu komurkowego, lub numer telefonu stacjonalnego poprzedzony dwucyfrowym numerek kierunkowym");
        
        ImageButton next = new ImageButton("res" + File.separator + "Dalej.png");
        next.setRolloverIcon(new ImageIcon("res" + File.separator + "DalejEntered.png"));
        next.setBounds(350, 510, 100, 40);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (!PoprawnoscDanych.poprawnosc(jtfPol[0].getText(), jtfPol[1].getText(), jtfPol[2].getText(), jtfPol[3].getText().replaceAll(" ", ""))) {
                    JOptionPane.showMessageDialog(null, "Proszę wpisać poprawne dane", "Error", INFORMATION_MESSAGE);
                    
                } else {
                    MainWindow.rightPanel.MakeOrderPart4(booking, jtfPol[0].getText(), jtfPol[1].getText(), jtfPol[2].getText(), Integer.parseInt(jtfPol[3].getText()));
                }
            }
        });
        add(next);
        repaint();
    }

    /**
     * Panel końcowy rezerwacji Należy potwierdzić poprawnyść wpisanych
     * wcześniej danych w przypadku błędnych danych istnieje możliwość
     * cołfnięćia się o 1 krok i poprawienia informacji po na ciśnięciu guzika
     * końcowego zostaje wysłany e-mail na podany wcześniej adres, wyświtla się
     * okno startowe programu
     *
     * @param booking - obiekt rezerwacji powstały w poprzednich krokach
     * @param name - imie
     * @param lastname - nazwisko
     * @param email - adres e-mail
     * @param number - numer telefonu
     */
    private void MakeOrderPart4(final Booking booking, final String name, final String lastname, final String email, final int number) {
        removeAll();
        setLayout(null);
        setBounds(BORDER, 0, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT);
        
        JLabel check = new JLabel("Sprawdź poprawność danych do rezerwacji");
        check.setBounds(50, 60, 700, 50);
        check.setForeground(WindowConstants.schematKolorow.getNazwy());
        check.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 20));
        check.setHorizontalAlignment(SwingConstants.CENTER);
        add(check);
        
        JLabel[] infoName = new JLabel[9];
        infoName[0] = new JLabel("Tytuł:");
        infoName[1] = new JLabel("Data:");
        infoName[2] = new JLabel("Godzina:");
        infoName[3] = new JLabel("Ilość miejsc:");
        infoName[4] = new JLabel("Imie:");
        infoName[5] = new JLabel("Nazwisko:");
        infoName[6] = new JLabel("Adres e-mail:");
        infoName[7] = new JLabel("Numer telefonu:");
        infoName[8] = new JLabel("Cena:");
        
        for (int i = 0; i < infoName.length; i++) {
            infoName[i].setBounds(50, 120 + 30 * i, 250, 30);
            infoName[i].setForeground(WindowConstants.schematKolorow.getNazwy());
            infoName[i].setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 16));
            infoName[i].setHorizontalAlignment(SwingConstants.RIGHT);
            add(infoName[i]);
        }
        ImageButton wstecz = new ImageButton("res" + File.separator + "Wstecz.png");
        wstecz.setRolloverIcon(new ImageIcon("res" + File.separator + "WsteczEntered.png"));
        wstecz.setBounds(WindowConstants.WIDTH, WindowConstants.WIDTH, WindowConstants.WIDTH, HEIGHT);
        wstecz.setBounds(290, 450, 100, 40);
        wstecz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow.rightPanel.MakeOrderPart3(booking);
            }
        });
        add(wstecz);
        ImageButton next = new ImageButton("res" + File.separator + "Dalej.png");
        next.setRolloverIcon(new ImageIcon("res" + File.separator + "DalejEntered.png"));
        next.setBounds(410, 450, 100, 40);
        
        JLabel[] info = new JLabel[9];
        info[0] = new JLabel(booking.getInfo()[0]);
        info[1] = new JLabel(booking.getInfo()[1]);
        info[2] = new JLabel(booking.getInfo()[2]);
        info[3] = new JLabel(Integer.toString(booking.listLength()));
        info[4] = new JLabel(name);
        info[5] = new JLabel(lastname);
        info[6] = new JLabel(email);
        info[7] = new JLabel(Integer.toString(number));
        DecimalFormat df = new DecimalFormat("#.00");
        info[8] = new JLabel(df.format(booking.price()) + " zł");
        
        for (int i = 0; i < info.length; i++) {
            info[i].setBounds(310, 120 + 30 * i, 390, 30);
            info[i].setForeground(WindowConstants.schematKolorow.getNapisy());
            info[i].setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 16));
            info[i].setHorizontalAlignment(SwingConstants.LEFT);
            add(info[i]);
        }
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DecimalFormat df = new DecimalFormat("#.00");
                boolean ok = booking.endBooking(name, lastname, email, number, Integer.toString(booking.listLength()), df.format(booking.price()) + " zł");
                if (ok) {
                    JOptionPane.showMessageDialog(null, "Proces rezerwacji przebiegł poprawnie", "Udana rezerwacja", INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Przepraszamy ale ktoś wyprzedził Cię w zajmowaniu miejsc", "Nieudana rezerwacja", INFORMATION_MESSAGE);
                }
                MainWindow.rightPanel.StartWindow();
            }
        });
        add(next);
        repaint();
    }

    /**
     * Funkcja wyświetlający informacje pasujące do zapytania z wyszukiwarki
     * filmy które zostały przyporządkowane są wyświtlane z pojedynczych
     * panelach, istnieje możliwość zmiany filmu o którym wyświtlane są
     * informacje, za pomocą kółeczek na górze panelu Po kliknięciu na nazwę
     * gatunku filmu, wyświtlają sie wszytskief filmy dostępne o danym gatunku
     *
     * @param idTab - tablica ID filmów do wyświetlenia
     */
    private void makeFound(final Integer[] idTab) {
        removeAll();
        setLayout(null);
        setBackground(WindowConstants.schematKolorow.getTlo());
        setBounds(BORDER, 0, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT);
        
        int ile = idTab.length;
        final JPanel panel = new JPanel();
        panel.setBounds(0, 50, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT - 50);
        panel.setLayout(null);
        panel.setBackground(WindowConstants.schematKolorow.getTlo());
        add(panel);
        if (ile > 0) {
            final ImageButton[] buttony = new ImageButton[ile];
            for (int i = 0; i < ile; i++) {
                final int k = i;
                buttony[k] = new ImageButton("res" + File.separator + "guzik.png");
                buttony[k].setRolloverIcon(new ImageIcon("res" + File.separator + "guzikEntered.png"));
                buttony[k].setBounds((int) ((WindowConstants.WIDTH - WindowConstants.BORDER) / 2 - ile * 10 + 20 * i), 30, 20, 20);
                add(buttony[k]);
                buttony[k].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ShowInfoFilm(idTab[k], panel);
                    }
                });
            }
            buttony[0].getActionListeners()[0].actionPerformed(new ActionEvent(buttony[0], 2, "symuluje"));
        } else {
            JLabel brakWynikow = new JLabel("Nie znaleziono psujących filmów");
            panel.add(brakWynikow);
            brakWynikow.setBounds(50, 150, 700, 100);
            brakWynikow.setForeground(WindowConstants.schematKolorow.getNazwy());
            brakWynikow.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 25));
            brakWynikow.setHorizontalAlignment(SwingConstants.CENTER);
        }
        repaint();
    }

    /**
     * Metoda wyświtlająca dostępne terminy dla danego fimu możliwe do
     * rezerwacji
     *
     * @param ID - id filmu dla którego sprawdzamy dostępne terminy
     */
    private void Terms(int ID) {
        removeAll();
        setLayout(null);
        setBackground(WindowConstants.schematKolorow.getTlo());
        setBounds(BORDER, 0, WindowConstants.WIDTH - BORDER, WindowConstants.HEIGHT);
        final Dates date = new Dates(ID);
        HashMap<String, Integer[]> hoursPerDay = date.getMapHoursOfMovie();
        String[] dates = date.getDatesOfMovie();
        MoviesDB bd = new MoviesDB();
        bd.open();
        JLabel title = new JLabel(bd.getTitle(ID));
        bd.close();
        add(title);
        title.setBounds(50, 60, 700, 50);
        title.setForeground(WindowConstants.schematKolorow.getNazwy());
        title.setFont(new Font(WindowConstants.czcionka2, Font.CENTER_BASELINE, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < 13; i++) {
            JLabel godz = new JLabel(Integer.toString(i + 10) + ":00");
            godz.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 12));
            godz.setForeground(WindowConstants.schematKolorow.getNazwy());
            godz.setBounds(140 + 50 * i, 110, 50, 30);
            add(godz);
        }
        for (int i = 0; i < dates.length; i++) {
            final JLabel data = new JLabel(dates[i]);
            data.setFont(new Font(WindowConstants.czcionka, Font.CENTER_BASELINE, 20));
            data.setForeground(WindowConstants.schematKolorow.getNazwy());
            add(data);
            data.setBounds(20, 150 + 40 * i, 110, 30);
            final Integer[] hours = hoursPerDay.get(dates[i]);
            for (int j = 0; j < hours.length; j++) {
                final int ind = j;
                if (hours[j] > 0) {
                    ImageButton godzina = new ImageButton("res" + File.separator + "Wyb.png");
                    godzina.setRolloverIcon(new ImageIcon("res" + File.separator + "WybEntered.png"));
                    godzina.setBounds(130 + 50 * j, 150 + 40 * i, 50, 30);
                    add(godzina);
                    godzina.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Booking b = new Booking();
                            b.startBooking(date.gettingTermsIDForFilm(hours[ind], data.getText()));
                            MainWindow.rightPanel.MakeOrderPart1(b);
                        }
                    });
                }
            }
        }
        repaint();
    }
}
