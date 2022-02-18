package UI;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DiceContainer {

    private BufferedImage[] dices;

    public DiceContainer() throws IOException {
        dices = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            dices[i] = ImageIO.read(getClass().getClassLoader().getResource("Dice" + String.valueOf(i + 1) + ".gif"));
        }
    }

    protected BufferedImage slikaKocke(int pik) {
        return dices[pik - 1];
    }
}