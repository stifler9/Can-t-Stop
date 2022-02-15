package Logika;

import java.util.HashMap;
import java.util.LinkedList;

public class Stanje {
    public final int igralcev;
    private LinkedList<StanjeIgralca> stanjaIgralcev;
    public int naVrsti;
    public HashMap<Integer, Integer> zasedeni; // polje - igralec
    
    public Stanje(int x) throws Exception{
        if(x > 6){
            throw new Exception("Too many players");
        }
        igralcev = x;
        naVrsti = 0;
        stanjaIgralcev = new LinkedList<StanjeIgralca>();
        stanjaIgralcev.clear();
        for(int i = 0; i < igralcev; i++){
            stanjaIgralcev.add(new StanjeIgralca());
        }
        zasedeni = new HashMap<Integer, Integer>();
    }

    public StanjeIgralca Get(int i){
        return stanjaIgralcev.get(i);
    }

    public StanjeIgralca GetCopy(int i){
        StanjeIgralca stanje = new StanjeIgralca();
        int num = 0;
        for(int x: Get(i).visine){
            stanje.visine[num] = x;
            num++;
        }
        return stanje;
    }

    public void Set(int i, StanjeIgralca stanje){
        stanjaIgralcev.set(i, stanje);
    }

    public void naslednji(){
        naVrsti++;
        if(naVrsti == igralcev){
            naVrsti = 0;
        }
    }

    public void zasediPolje(int polje){
        for(int i = 0; i < stanjaIgralcev.size(); i++){
            if(i != naVrsti){
                Get(i).vrziDol(polje);
            }
        }
        zasedeni.put(polje, naVrsti);
    }
}
