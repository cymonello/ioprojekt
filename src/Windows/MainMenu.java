/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Bartłomiej
 */
public class MainMenu extends JPanel implements ActionListener {
    public MainMenu(){
        setLayout(null);
        JLabel logo = new JLabel( new ImageIcon(WindowConstants.LOGOIMG));
        logo.setBounds(0,0,200,100);
        add(logo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
