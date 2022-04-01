package UI;

import java.awt.Color;
import java.awt.Graphics;

public class PositionLoader {

    private static int sirinaPolja;
    private static int visinaPolja;

    private static int sirinaIgralca;

    private static int stranicaKocke;
    private static int dxKocke;
    private static int dyKocke;

    // #region Setters
    protected static void setSirinaVisina(int sirina, int visina) {
        sirinaPolja = sirina;
        visinaPolja = visina;
    }

    protected static void setSirinaIgralca(int sirina) {
        sirinaIgralca = sirina;
    }

    protected static void setMereKock(int stranica, int dx, int dy) {
        stranicaKocke = stranica;
        dxKocke = dx;
        dyKocke = dy;
    }
    // #endregion Setters

    private static boolean klikZnotraj(int x, int y, int[] pozicija) {
        // pos - [x, y, sirina, visina]
        if ((x < pozicija[0]) | (x > pozicija[0] + pozicija[2])) {
            return false;
        }
        if ((y < pozicija[1]) | (y > pozicija[1] + pozicija[3])) {
            return false;
        }
        return true;
    }

    // #region Polje
    private static int[] getPositionPolje(int polje, int visoko) {
        return new int[] { (polje - 2) * sirinaPolja, (12 - visoko) * visinaPolja };
    }

    protected static void izrisiPolje(Graphics g, int polje, int visoko) {
        int[] pozicija = getPositionPolje(polje, visoko);
        g.drawRect(pozicija[0], pozicija[1], sirinaPolja - 1, visinaPolja - 1);
    }

    protected static void napisiPolje(Graphics g, String ime, int polje, int visoko) {
        int[] pozicija = getPositionPolje(polje, visoko);
        g.drawString(ime, pozicija[0] + (int) (2 * sirinaPolja / 5), pozicija[1] + 2 * (int) (visinaPolja / 3));
    }
    // #endregion Polje

    // #region Igralci
    private static int[] getPositionIgralec(int polje, int visoko, int igralec) {
        return new int[] { (polje - 2) * sirinaPolja + (sirinaIgralca * igralec) + 1, (13 - visoko) * visinaPolja + 1 };
    }

    protected static void izrisiIgralca(Graphics g, int polje, int visoko, int igralec) {
        int[] pozicija = getPositionIgralec(polje, visoko, igralec);
        g.fillRect(pozicija[0], pozicija[1], sirinaIgralca, visinaPolja - 2);
    }
    // #endregion Igralci

    private static int[] getPositionZasedenost(int polje, int visinastolpca) {
        return new int[] { (polje - 2) * sirinaPolja + (int) (sirinaPolja / 4),
                (13 - visinastolpca) * visinaPolja + (int) (visinaPolja / 4),
                sirinaPolja - 2 * (int) (sirinaPolja / 4),
                visinastolpca * visinaPolja - 2 * (int) (visinaPolja / 4) };
    }

    protected static void izrisiZasedenost(Graphics g, int polje, int visinastolpca) {
        int[] pozicija = getPositionZasedenost(polje, visinastolpca);
        g.fillRect(pozicija[0], pozicija[1], pozicija[2], pozicija[3]);
    }

    private static int[] getPositionZacetek(int polje, int visina) {
        return new int[] { polje * sirinaPolja + (int) (sirinaPolja / 3),
                (13 - visina) * visinaPolja + (int) (visinaPolja / 3),
                sirinaPolja - 2 * (int) (sirinaPolja / 3),
                visinaPolja - 2 * (int) (visinaPolja / 3) };
    }

    protected static void izrisiZacetek(Graphics g, int polje, int visina) {
        int[] pozicija = getPositionZacetek(polje, visina);
        g.fillOval(pozicija[0], pozicija[1], pozicija[2], pozicija[3]);
        g.setColor(Color.BLACK);
        g.drawOval(pozicija[0], pozicija[1], pozicija[2], pozicija[3]);
    }

    // #region Met
    private static int[] getPositionMet() {
        return new int[] { 0, 0, 2 * sirinaPolja, visinaPolja - 1 };
    }

    protected static void izrisiMet(Graphics g) {
        int[] pozicija = getPositionMet();
        g.drawRect(pozicija[0], pozicija[1], pozicija[2], pozicija[3]);
        g.drawString("Met!", pozicija[0] + (int) (sirinaPolja / 3), pozicija[1] + 2 * (int) (visinaPolja / 3));
    }

    protected static boolean klikMet(int x, int y) {
        return klikZnotraj(x, y, getPositionMet());
    }
    // #endregion Met

    private static int[] getPositionPodMet() {
        return new int[] { 0, visinaPolja, 2 * sirinaPolja, 4 * visinaPolja - 1 };
    }

    protected static void izrisiPodMet(Graphics g) {
        int[] pozicija = getPositionPodMet();
        g.drawRect(pozicija[0], pozicija[1], pozicija[2], pozicija[3]);
    }

    private static int[] getPositionKocke(int k) {
        return new int[] { dxKocke + (k * stranicaKocke) + 2, dyKocke + 2, stranicaKocke - 4, stranicaKocke - 4 };
    }

    protected static void izrisiKocko(Graphics g, int k, int stevilka) {
        int[] pozicija = getPositionKocke(k);
        g.drawImage(DiceContainer.slikaKocke(stevilka), pozicija[0], pozicija[1], pozicija[2], pozicija[3], null);
    }

    // #region Odlocitve
    private static int[] getPositionOdlocitev(int o, boolean leva) {
        return new int[] { (leva ? 0 : sirinaPolja), (2 + o) * visinaPolja, sirinaPolja - 1, visinaPolja - 1 };
    }

    protected static void izrisiOdlocitev(Graphics g, int o, boolean leva, int stevilka) {
        int[] pozicija = getPositionOdlocitev(o, leva);
        g.drawRect(pozicija[0], pozicija[1], pozicija[2], pozicija[3]);
        g.drawString(String.valueOf(stevilka), pozicija[0] + (int) (sirinaPolja / 3),
                pozicija[1] + 2 * (int) (visinaPolja / 3));
    }

    protected static boolean klikOdlocitev(int x, int y, int o, boolean leva) {
        return klikZnotraj(x, y, getPositionOdlocitev(o, leva));
    }
    // #endregion Odlocitve

    // #region Zakljucek
    private static int[] getPositionZakljucek() {
        return new int[] { 0, 5 * visinaPolja, 2 * sirinaPolja, visinaPolja };
    }

    protected static void izrisiZakljucek(Graphics g) {
        int[] pozicija = getPositionZakljucek();
        g.drawRect(pozicija[0], pozicija[1], pozicija[2], pozicija[3]);
        g.drawString("Zakljuci!", pozicija[0] + (int) (sirinaPolja / 3), pozicija[1] + 2 * (int) (visinaPolja / 3));
    }

    protected static boolean klikZakljucek(int x, int y) {
        return klikZnotraj(x, y, getPositionZakljucek());
    }
    // #endregion Zakljucek
}
