package UI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DiceContainer {

    private BufferedImage dice1;
    private BufferedImage dice2;
    private BufferedImage dice3;
    private BufferedImage dice4;
    private BufferedImage dice5;
    private BufferedImage dice6;

    public DiceContainer() throws IOException{
        dice1 = ImageIO.read(new File(".\\Images\\dice1.gif"));
        dice2 = ImageIO.read(new File(".\\Images\\dice2.gif"));
        dice3 = ImageIO.read(new File(".\\Images\\dice3.gif"));
        dice4 = ImageIO.read(new File(".\\Images\\dice4.gif"));
        dice5 = ImageIO.read(new File(".\\Images\\dice5.gif"));
        dice6 = ImageIO.read(new File(".\\Images\\dice6.gif"));
    }

    protected BufferedImage slikaKocke(int pik){
        switch(pik){
            case 1:
                return dice1;
            case 2:
                return dice2;
            case 3:
                return dice3;
            case 4:
                return dice4;
            case 5:
                return dice5;
            case 6:
                return dice6;
            default:
                return dice1;
        }
    }

}
