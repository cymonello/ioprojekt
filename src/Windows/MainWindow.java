/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Bartłomiej
 */
public class MainWindow extends JFrame implements ActionListener {

    public static void main(String[] args) {
        run(new MainWindow(), WindowConstants.WIDTH, WindowConstants.HEIGHT, "Okno testowe");
    }
    
    private JPanel leftPanel;
    private JScrollPane rightPanelScroll;
    private JPanel rightPanel;
    
    public MainWindow(){
        leftPanel = new MainMenu();
        leftPanel.setBackground(WindowConstants.MAINMENUCOLOR);
        leftPanel.setBounds(0, 0,WindowConstants.BORDER , WindowConstants.HEIGHT);
        add(leftPanel);
        rightPanel = new RightWindow();
        rightPanelScroll = new JScrollPane(rightPanel);
        rightPanelScroll.setBounds(WindowConstants.BORDER,0,WindowConstants.WIDTH - WindowConstants.BORDER,WindowConstants.HEIGHT);
        add(rightPanelScroll);
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
