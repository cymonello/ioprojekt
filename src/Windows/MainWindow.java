package Windows;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Klasa powodująca wyświetlenie się głównego okna programu, łączy ona ze sobą
 * lewy panel - menu, oraz prawy w którym wyświetlane są konkretne informacje
 *
 * @author Bartłomiej
 */
public class MainWindow extends JFrame {

    /**
     * Statyczna metoda main powoduję wyświtlenie głównego programu main poprzez
     * wywołanie konstruktora klasy MainWindow oraz statycznej funkcji run
     *
     * @param args
     */
    public static void main(String[] args) {
        MainWindow main = new MainWindow();
        run(main, WindowConstants.WIDTH, WindowConstants.HEIGHT, "IO_Projekt_rezerwacji_w_kinie");

    }
    //zmienne pakietowe, aby móc z nich korzystyć w funkcjach klasy RightWindow
    static MainMenu leftPanel;
    static RightWindow rightPanel;

    /**
     * Konstrukor klasy MainWindow Tworzy nowe obiekt odpowiedzialne za
     * wyświtlanie lewgo i prawego panelu w oknie główym programu, dodaje te
     * panele do JFrame (siebie)
     */
    public MainWindow() {
        rightPanel = new RightWindow();
        rightPanel.setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);
        add(rightPanel);

        leftPanel = new MainMenu();
        leftPanel.setBounds(0, 0, WindowConstants.BORDER, WindowConstants.HEIGHT);
        add(leftPanel);
        pack();

    }

    /**
     * Funkcja przez którą uruchamiamy okna naszej aplikacji.
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
}
