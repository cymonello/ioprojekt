package Windows;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

/**
 * Klasa powodująca wyświetlenie się głównego okna programu, łączy ona ze sobą
 * lewy panel - menu, oraz prawy w którym wyświetlane są konkretne informacje
 *
 * @author Bartłomiej
 */
public class MainWindow extends JFrame {

    /**
     * Tu jest metoda main powodująca wyświetlenie okna
     *
     * @param args
     */
    public static void main(String[] args) {
;        MainWindow main = new MainWindow();
        run(main, WindowConstants.WIDTH, WindowConstants.HEIGHT, "Projekt - prototyp 2");

    }

    static MainMenu leftPanel;
    static RightWindow rightPanel;

    public MainWindow() {
        rightPanel = new RightWindow();
        rightPanel.setBounds(WindowConstants.BORDER, 0, WindowConstants.WIDTH - WindowConstants.BORDER, WindowConstants.HEIGHT);
        add(rightPanel);

        leftPanel = new MainMenu();
        leftPanel.setBackground(WindowConstants.MAINMENUCOLOR);
        leftPanel.setBounds(0, 0, WindowConstants.BORDER, WindowConstants.HEIGHT);
        add(leftPanel);

    }

    /**
     * Funkcja przez którą uruchamiamy okna naszej aplikacji Nasza aplikacja
     * niestety jest jednowątkowa, nie powinno być to jednak widoczne, ale gdyby
     * coś było nie tak, to nie można robić jednocześnie 2 poleceń pracujących
     * na jednej zmiennej
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
                /*f.setUndecorated(true);
                f.getRootPane().setWindowDecorationStyle(
                        JRootPane.FRAME
                );
                MetalTheme th = MetalLookAndFeel.getCurrentTheme();
                MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme() {
                    @Override
                    public ColorUIResource getWindowTitleInactiveBackground() {
                        return new ColorUIResource(Color.black);
                    }

                    @Override
                    public ColorUIResource getWindowTitleBackground() {
                        return new ColorUIResource(Color.black);
                    }

                    @Override
                    public ColorUIResource getPrimaryControlHighlight() {
                        return new ColorUIResource(Color.black);
                    }

                    @Override
                    public ColorUIResource getPrimaryControlDarkShadow() {
                        return new ColorUIResource(Color.black);
                    }

                    @Override
                    public ColorUIResource getPrimaryControl() {
                        return new ColorUIResource(Color.black);
                    }

                    @Override
                    public ColorUIResource getControlHighlight() {
                        return new ColorUIResource(Color.black);
                    }

                    @Override
                    public ColorUIResource getControlDarkShadow() {
                        return new ColorUIResource(Color.black);
                    }

                    @Override
                    public ColorUIResource getControl() {
                        return new ColorUIResource(Color.black);
                    }
                    //end inActiveBumps

                });

                try {
                    UIManager.setLookAndFeel(
                            UIManager.getCrossPlatformLookAndFeelClassName()
                    );
                } catch (UnsupportedLookAndFeelException e) {
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

                SwingUtilities.updateComponentTreeUI(f);*/
                f.setVisible(true);
            }

        });
    }
}
