/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;
//Bartek nie umie 
import database.MoviesDB;
import database.TermsDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Bartłomiej
 */
public class MainWindow extends JFrame implements ActionListener {

    public static void main(String[] args) {
        MainWindow main = new MainWindow();
        run(main, WindowConstants.WIDTH, WindowConstants.HEIGHT, "Okno testowe");
    }
    
    public static JPanel leftPanel;
    //public static JScrollPane rightPanelScroll;
    public static JPanel rightPanel;
    
    public MainWindow(){
        leftPanel = new MainMenu();
        leftPanel.setBackground(WindowConstants.MAINMENUCOLOR);
        leftPanel.setBounds(0, 0,WindowConstants.BORDER , WindowConstants.HEIGHT);
        add(leftPanel);
        
        rightPanel = new RightWindow();
        rightPanel.setBounds(WindowConstants.BORDER,0,WindowConstants.WIDTH - WindowConstants.BORDER,WindowConstants.HEIGHT);
        add(rightPanel);
    }
    
    /**
     * Funkcja przez którą uruchamiamy okna naszej aplikacji
     *
     * @param f - obiekt ktory moze wyświetlić okno, musi dziedzyczyć po JFrame
     * @param width - szereokość okno w pikselach
     * @param height - wysokość okna w pikselach
     * @param windowName - nazwa okna wyświetlająca się na pasko obok ikonki
     */
    public static void run(final JFrame f, final int width, final int height, final String windowName) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                f.setTitle(windowName);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setSize(width, height);
                f.setResizable(false);
                f.setVisible(true);
            }

        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
