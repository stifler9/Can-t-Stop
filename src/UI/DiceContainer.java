package UI;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DiceContainer {

    private static BufferedImage[] dices;

    protected static void Initialize(Platno platno) throws IOException {
        if(dices != null) {
            return;
        }
        dices = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            dices[i] = ImageIO.read(platno.getClass().getClassLoader().getResource("Dice" + String.valueOf(i + 1) + ".png"));
        }
    }

    protected static BufferedImage slikaKocke(int pik) {
        return dices[pik - 1];
    }
}