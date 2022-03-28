package Logika;

import java.util.LinkedList;
import java.util.Random;

public class Igra {

    public Stanje stanje;

    public StanjeIgralca prejsnjeStanjeIgralca;

    public int[] vrzeneKocke;
    public LinkedList<Integer> cifreVIgri; // Lahko Set<>
    public LinkedList<Integer> veljavneOdlocitve; // 0-2

    private boolean neomejenoZmag;

    private static Random random;

    public Igra(int i) throws Exception {
        stanje = new Stanje(i);

        prejsnjeStanjeIgralca = stanje.GetCopy(stanje.naVrsti);

        vrzeneKocke = null;
        cifreVIgri = new LinkedList<Integer>();
        veljavneOdlocitve = new LinkedList<Integer>();

        random = new Random();
    }

    public boolean getNeomejenoZmag() {
        return neomejenoZmag;
    }

    public void setNeomejenoZmag(boolean neomejenoZmag) {
        this.neomejenoZmag = neomejenoZmag;
        if (neomejenoZmag) {
            stanje.stanjeIgre = StanjeIgre.VTEKU;
        } else {
            stanje.setStatusIgre();
        }
    }

    public void Met() throws Exception {
        if (stanje.stanjeIgre != StanjeIgre.VTEKU) {
            return;
        }

        if (vrzeneKocke != null) {
            return;
        }

        vrzeneKocke = new int[4];
        for (int i = 0; i < 4; i++) {
            vrzeneKocke[i] = random.nextInt(6) + 1;
        }

        for (int i = 0; i < 3; i++) {
            if (veljavnaOdlocitev(i)) {
                veljavneOdlocitve.add(i);
            }
        }
    }

    private boolean veljavnaOdlocitev(int i) throws Exception {
        int[] cifre = cifreOdlocitve(i);
        return (lahkoIgra(cifre[0]) | lahkoIgra(cifre[1]));
    }

    public void odigraj(int opcija, boolean leva /* leva cifra ali desna */) throws Exception {
        if (stanje.stanjeIgre != StanjeIgre.VTEKU) {
            return;
        }

        if (veljavneOdlocitve.contains(opcija)) {
            StanjeIgralca stanjeIgralcaNaVrsti = stanje.Get(stanje.naVrsti);
            int[] cifre = cifreOdlocitve(opcija);

            if (leva) {
                if (lahkoIgra(cifre[0])) {
                    stanjeIgralcaNaVrsti.premakni(cifre[0]);
                    dodajCifroOdlocitve(cifre[0]);
                }
                if (lahkoIgra(cifre[1])) {
                    stanjeIgralcaNaVrsti.premakni(cifre[1]);
                    dodajCifroOdlocitve(cifre[1]);
                }
            } else {
                if (lahkoIgra(cifre[1])) {
                    stanjeIgralcaNaVrsti.premakni(cifre[1]);
                    dodajCifroOdlocitve(cifre[1]);
                }
                if (lahkoIgra(cifre[0])) {
                    stanjeIgralcaNaVrsti.premakni(cifre[0]);
                    dodajCifroOdlocitve(cifre[0]);
                }
            }

            vrzeneKocke = null;
            veljavneOdlocitve.clear();
        }
    }

    public boolean lahkoIgra(int polje) throws Exception {
        if (cifreVIgri.size() >= 3) {
            if (cifreVIgri.contains(polje)) {
                return !stanje.Get(stanje.naVrsti).naVrhu(polje);
            } else {
                return false;
            }
        } else {
            if (stanje.zasedeni.containsKey(polje)) {
                return false;
            } else {
                return !stanje.Get(stanje.naVrsti).naVrhu(polje);
            }
        }
    }

    private void dodajCifroOdlocitve(int cifra) {
        if (!cifreVIgri.contains(cifra)) {
            cifreVIgri.add(cifra);
        }
    }

    public int[] cifreOdlocitve(int i) {
        int[] rez = new int[2];
        if (vrzeneKocke != null) {
            if (i == 0) {
                rez[0] = vrzeneKocke[0] + vrzeneKocke[1];
                rez[1] = vrzeneKocke[2] + vrzeneKocke[3];
            } else if (i == 1) {
                rez[0] = vrzeneKocke[0] + vrzeneKocke[2];
                rez[1] = vrzeneKocke[1] + vrzeneKocke[3];
            } else {
                rez[0] = vrzeneKocke[0] + vrzeneKocke[3];
                rez[1] = vrzeneKocke[2] + vrzeneKocke[1];
            }
        }
        return rez;
    }

    public void zakljuci() throws Exception {
        if (stanje.stanjeIgre != StanjeIgre.VTEKU) {
            return;
        }

        if (veljavneOdlocitve.isEmpty() & (vrzeneKocke != null)) {
            stanje.Set(stanje.naVrsti, prejsnjeStanjeIgralca);
        } else {
            for (int polje = 2; polje < 13; polje++) {
                if (stanje.Get(stanje.naVrsti).naVrhu(polje)) {
                    stanje.zasediPolje(polje);
                    stanje.zasedeni.put(polje, stanje.naVrsti);
                }
            }
        }
        clearIgralneSpr();
        stanje.naslednji();

        prejsnjeStanjeIgralca = stanje.GetCopy(stanje.naVrsti);

        if (!neomejenoZmag) {
            stanje.setStatusIgre();
        }
    }

    private void clearIgralneSpr() {
        vrzeneKocke = null;
        veljavneOdlocitve.clear();
        cifreVIgri.clear();
    }
}