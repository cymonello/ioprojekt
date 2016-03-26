/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Ta klasa jest jedynie po to aby w jednym miejscu przechowywać wszystkie stałe parametry dla okna, z biegiem czasu pewnie ich przybedzie, nie byłoby sensu przechowywania ich w wielu miejscach
 * ewentualana późniejsza zmiana w tym miejscy powoduje zmiane tych parametrów w całym programie
 * @author Bartłomiej
 */
abstract public class WindowConstants {

    public static final int HEIGHT = 600;
    public static final int WIDTH = 1000;
    public static final int BORDER = 200;
    public static final Color MAINMENUCOLOR = Color.black;
    public static final Image LOGOIMG = new ImageIcon("res/KinoLogo.png").getImage();
}
