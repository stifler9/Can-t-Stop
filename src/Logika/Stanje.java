package Logika;

import java.util.HashMap;
import java.util.LinkedList;

public class Stanje {
    public final int igralcev;
    private LinkedList<StanjeIgralca> stanjaIgralcev;
    public int naVrsti;
    public HashMap<Integer, Integer> zasedeni; // polje - igralec

    public StanjeIgre stanjeIgre;

    public Stanje(int x) throws Exception {
        if (x > 6) {
            throw new Exception("Too many players");
        }
        igralcev = x;
        naVrsti = 0;
        stanjaIgralcev = new LinkedList<StanjeIgralca>();
        stanjaIgralcev.clear();
        for (int i = 0; i < igralcev; i++) {
            stanjaIgralcev.add(new StanjeIgralca());
        }
        zasedeni = new HashMap<Integer, Integer>();
        stanjeIgre = StanjeIgre.VTEKU;
    }

    public StanjeIgralca Get(int i) {
        return stanjaIgralcev.get(i);
    }

    public StanjeIgralca GetCopy(int i) {
        StanjeIgralca stanje = new StanjeIgralca();
        int num = 0;
        for (int x : Get(i).visine) {
            stanje.visine[num] = x;
            num++;
        }
        return stanje;
    }

    public void Set(int i, StanjeIgralca stanje) {
        stanjaIgralcev.set(i, stanje);
    }

    public void naslednji() {
        naVrsti = (naVrsti + 1) % igralcev;
    }

    public void zasediPolje(int polje) {
        for (int i = 0; i < stanjaIgralcev.size(); i++) {
            if (i != naVrsti) {
                Get(i).vrziDol(polje);
            }
        }
        zasedeni.put(polje, naVrsti);
    }

    protected void setStatusIgre() {
        HashMap<Integer, Integer> zmageIgralcev = new HashMap<Integer, Integer>();
        for (int i = 0; i < igralcev; i++) {
            zmageIgralcev.put(i, 0);
        }
        for (int igralec : zasedeni.values()) {
            int zmag = zmageIgralcev.get(igralec);
            if (++zmag >= 3) {
                stanjeIgre = zmagalIgralec(igralec);
                break;
            }
            zmageIgralcev.put(igralec, zmag);
        }
    }

    private StanjeIgre zmagalIgralec(int igralec) {
        switch (igralec) {
            case 0:
                return StanjeIgre.ZMAGA1;
            case 1:
                return StanjeIgre.ZMAGA2;
            case 2:
                return StanjeIgre.ZMAGA3;
            case 3:
                return StanjeIgre.ZMAGA4;
            case 4:
                return StanjeIgre.ZMAGA5;
            case 5:
                return StanjeIgre.ZMAGA6;
            default:
                return StanjeIgre.VTEKU;
        }
    }
}
