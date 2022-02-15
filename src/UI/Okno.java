package UI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import Logika.Igra;

public class Okno extends JFrame implements ActionListener {
    Igra cantstop;
    Platno platno;

    JMenu mainMenu;
    JMenuItem pobarvaj;

    public Okno(int igralcev) throws Exception{
        cantstop = new Igra(igralcev);

        this.setTitle("Can't Stop");
        
        BufferedImage iconImage = ImageIO.read(new File(".\\Images\\diceIcon.png"));
        setIconImage(iconImage);
        // this.setSize(1000, 800);

        // TODO clean
        // mainMenu = new JMenu("Igra");
        // pobarvaj = new JMenuItem("Pobarvaj");
        // pobarvaj.setVisible(true);
        // mainMenu.add(pobarvaj);
        // mainMenu.setVisible(true);
        // mainMenu.addActionListener(this);
        // this.add(mainMenu, 0);
        
        platno = new Platno(this);
        platno.setVisible(true);
        this.add(platno, 0);
        
        this.setSize(1000, 1000);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.toFront();
        this.setVisible(true);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}