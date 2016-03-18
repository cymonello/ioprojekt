/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Windows;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.JPanel;

/**
 *
 * @author Bartłomiej
 */
public class MainMenu extends JPanel implements ActionListener{
    private ImageButton logo;
    public MainMenu(){
        setLayout(null);
        logo = new ImageButton("res/KinoLogo.png");
        logo.setRolloverIcon(new ImageIcon("res/KinoLogoEntered.png"));
        logo.addActionListener(this);
        add(logo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == logo){
            JOptionPane.showMessageDialog(null, "Czesc", "Po co to klikasz?", INFORMATION_MESSAGE);
        }
    }
}
class ImageButton extends JButton {

  public ImageButton(String img) {
    this(new ImageIcon(img));
  }

  public ImageButton(ImageIcon icon) {
    setIcon(icon);
    setMargin(new Insets(0, 0, 0, 0));
    setIconTextGap(0);
    setBorderPainted(false);
    setBorder(null);
    setText(null);
    setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
  }

}