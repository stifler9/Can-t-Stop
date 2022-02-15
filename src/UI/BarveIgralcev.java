package UI;

import java.awt.Color;

public class BarveIgralcev {
    public static Color GetColor(int igralec){
        if(igralec == 0){
            return Color.BLUE;
        }
        if(igralec == 1){
            return Color.YELLOW;
        }
        if(igralec == 2){
            return Color.RED;
        }
        if(igralec == 3){
            return Color.GREEN;
        }
        if(igralec == 4){
            return Color.PINK;
        }
        if(igralec == 5){
            return new Color(102, 0, 204);
        }
        return Color.BLACK;
    }
}
