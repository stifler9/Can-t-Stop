package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import Logika.MaxVisine;
import Logika.StanjeIgralca;

public class Platno extends JPanel implements MouseListener {

    public Okno master;

    public Platno(Okno master) {
        super();

        setBackground(Color.BLACK);
        addMouseListener(this);
        this.master = master;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(11*100, 13*60);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // igralno polje
        int visina = (int)(getBounds().height / 13);
        int sirina = (int)(getBounds().width / 11);
        
        // polje - Max 13 visina
        // TODO cifre
        for(int polje = 2; polje < 13; polje++){
            int maxVisina;
            try {
                maxVisina = MaxVisine.MaxVisina(polje);
                for(int visoko = 0; visoko < maxVisina; visoko++){
                    if(visoko == MaxVisine.MaxVisina(polje)-1){
                        g.setColor(Color.YELLOW);
                    }else{
                        g.setColor(Color.WHITE);
                    }
                    g.drawRect((polje-2)*sirina, (12 - visoko)*visina, sirina, visina);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Igralci
        int sirinaIgralca = (int)(sirina / master.cantstop.stanje.igralcev);
        for(int igralec = 0; igralec < master.cantstop.stanje.igralcev; igralec++){
            // polje - Max 13 visina
            StanjeIgralca stanjeI = master.cantstop.stanje.Get(igralec);
            for(int polje = 0; polje < 11; polje++){
                int visoko = stanjeI.visine[polje];
                if(visoko > 0){
                    g.setColor(BarveIgralcev.GetColor(igralec));
                    g.fillRect(polje*sirina + (sirinaIgralca*igralec), (13 - visoko)*visina, sirinaIgralca, visina);
                }
            }
        }

        // zasedenost
        // TODO lepse oznaci
        for(int polje: master.cantstop.stanje.zasedeni.keySet()){
            g.setColor(BarveIgralcev.GetColor(master.cantstop.stanje.zasedeni.get(polje)));
            try {
                int visinastolpca = MaxVisine.MaxVisina(polje);
                g.fillRect((polje-2)*sirina, (13 - visinastolpca)*visina, sirina, visinastolpca*visina);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // iz kje je zacel
        int polje = 0;
        g.setColor(BarveIgralcev.GetColor(master.cantstop.stanje.naVrsti));
        for(int zacetnaVisina: master.cantstop.prejsnjeStanjeIgralca.visine){
            if(zacetnaVisina > 0){
                g.fillOval(polje*sirina + (int)(sirina/3), (13 - zacetnaVisina)*visina + (int)(visina/3), sirina - 2*(int)(sirina/3), visina - 2*(int)(visina/3));
            }
            polje++;
        }


        if(master.cantstop.vrzeneKocke == null){
            g.setColor(Color.WHITE);
            g.drawRect(0, 0, 2*sirina, visina);
            // g.setFont(new Font());
            g.drawString("Met!", (int)(sirina / 3), (int)(visina / 3));
        }else{
            // TODO Slike kock

            g.setColor(Color.WHITE);
            g.drawRect(0, 0, 2*sirina, visina);
            String kockeStr = "";
            for(int kocka: master.cantstop.vrzeneKocke){
                if(kockeStr == ""){
                    kockeStr = String.valueOf(kocka);
                }else{
                    kockeStr += ", " + String.valueOf(kocka);
                }
            }
            g.drawString(kockeStr, (int)(sirina / 3), (int)(visina / 3));

            try{
                // 1. odlocitev
                int[] cifreOdlocitve = master.cantstop.cifreOdlocitve(0);
                if(!master.cantstop.lahkoIgra(cifreOdlocitve[0])){
                    g.setColor(Color.RED);
                }else{
                    g.setColor(Color.GREEN);
                }
                g.drawRect(0, visina, sirina, visina);
                g.drawString(String.valueOf(cifreOdlocitve[0]), (int)(sirina/3), visina + (int)(visina/2));
                if(!master.cantstop.lahkoIgra(cifreOdlocitve[1])){
                    g.setColor(Color.RED);
                }else{
                    g.setColor(Color.GREEN);
                }
                g.drawRect(sirina, visina, sirina, visina);
                g.drawString(String.valueOf(cifreOdlocitve[1]), sirina + (int)(sirina/3), visina + (int)(visina/2));


                // 2. odlocitev
                cifreOdlocitve = master.cantstop.cifreOdlocitve(1);
                if(!master.cantstop.lahkoIgra(cifreOdlocitve[0])){
                    g.setColor(Color.RED);
                }else{
                    g.setColor(Color.GREEN);
                }
                g.drawRect(0, 2*visina, sirina, visina);
                g.drawString(String.valueOf(cifreOdlocitve[0]), (int)(sirina/3), 2*visina + (int)(visina/2));
                if(!master.cantstop.lahkoIgra(cifreOdlocitve[1])){
                    g.setColor(Color.RED);
                }else{
                    g.setColor(Color.GREEN);
                }
                g.drawRect(sirina, 2*visina, sirina, visina);
                g.drawString(String.valueOf(cifreOdlocitve[1]), sirina + (int)(sirina/3), 2*visina + (int)(visina/2));


                // 3. odlocitev
                cifreOdlocitve = master.cantstop.cifreOdlocitve(2);
                if(!master.cantstop.lahkoIgra(cifreOdlocitve[0])){
                    g.setColor(Color.RED);
                }else{
                    g.setColor(Color.GREEN);
                }
                g.drawRect(0, 3*visina, sirina, visina);
                g.drawString(String.valueOf(cifreOdlocitve[0]), (int)(sirina/3), 3*visina + (int)(visina/2));
                if(!master.cantstop.lahkoIgra(cifreOdlocitve[1])){
                    g.setColor(Color.RED);
                }else{
                    g.setColor(Color.GREEN);
                }
                g.drawRect(sirina, 3*visina, sirina, visina);
                g.drawString(String.valueOf(cifreOdlocitve[1]), sirina + (int)(sirina/3), 3*visina + (int)(visina/2));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        if(master.cantstop.veljavneOdlocitve.isEmpty()){
            g.setColor(Color.white);
            g.drawRect(2*sirina, 0, 2*sirina, visina);
            g.drawString("Zakljuci!", 2*sirina + (int)(sirina / 3), (int)(visina / 3));
        }

        g.setColor(Color.WHITE);
        String igralecLbl;
        int stIgr = master.cantstop.stanje.igralcev;
        if(stIgr == 1){
            igralecLbl = " igralec";
        }else if(stIgr == 2){
            igralecLbl = " igralca";
        }else if(stIgr < 4){
            igralecLbl = " igralci";
        }else{
            igralecLbl = " igralcev";
        }
        g.drawString(String.valueOf(master.cantstop.stanje.igralcev) + igralecLbl, 10*sirina, (int)(visina/2));
    }

    private void klik(int x, int y){
        int visina = (int)(getBounds().height / 13);
        int sirina = (int)(getBounds().width / 11);

        if(master.cantstop.vrzeneKocke == null){
            if((x <= 2*sirina) & (y <= visina)){
                try {
                    master.cantstop.Met();
                    master.osveziUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            if((x <= 2*sirina) & (y >= visina) & (y < 4*visina)){
                boolean levo = (x <= sirina);
                int opcija = (int)((y - visina)/visina);

                try {
                    master.cantstop.odigraj(opcija, levo);
                    master.osveziUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(master.cantstop.veljavneOdlocitve.isEmpty()){
            if((x > 2*sirina) & (x <= 4*sirina) & (y < visina)){
                try {
                    master.cantstop.zakljuci();
                    master.osveziUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        klik(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {   
    }

    @Override
    public void mouseEntered(MouseEvent e) { 
    }

    @Override
    public void mouseExited(MouseEvent e) { 
    }
}