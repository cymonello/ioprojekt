/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Klasa powodująca wyświetlenie się głównego okna programu, łączy ona ze sobą lewy panel - menu, oraz prawy w którym wyświetlane są konkretne informacje
 * @author Bartłomiej
 */
public class MainWindow extends JFrame{
    /**
     * Tu jest narazie metoda main powodująca wyświetlenie okna, żeby nie irytowała was przy kompilacji/testowaniu tego co piszecie
     */
    public static void main(String[] args) {
        MainWindow main = new MainWindow();
        run(main, WindowConstants.WIDTH, WindowConstants.HEIGHT, "Okno testowe");
    }
    
    public static JPanel leftPanel;
    public static JPanel rightPanel;
    
    public MainWindow(){
        leftPanel = new MainMenu();
        leftPanel.setBackground(WindowConstants.MAINMENUCOLOR);
        leftPanel.setBounds(0, 0,WindowConstants.BORDER , WindowConstants.HEIGHT);
        add(leftPanel);
        
        rightPanel = new RightWindow(0);
        rightPanel.setBounds(WindowConstants.BORDER,0,WindowConstants.WIDTH - WindowConstants.BORDER,WindowConstants.HEIGHT);
        add(rightPanel);
    }
    
    /**
     * Funkcja przez którą uruchamiamy okna naszej aplikacji
     * Nasza aplikacja niestety jest jednowątkowa, nie powinno być to jednak widoczne, ale gdyby coś było nie tak, to nie można robić jednocześnie 2 poleceń pracujących na jednej zmiennej
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
}
