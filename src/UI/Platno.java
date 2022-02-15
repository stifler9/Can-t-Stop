package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import Logika.MaxVisine;
import Logika.StanjeIgralca;

public class Platno extends JPanel implements MouseInputListener {

    public Okno master;

    public Platno(Okno master) {
        super();

        this.master = master;
        this.setSize(11*100, 13*60);
        setBackground(Color.BLACK);
        this.addMouseListener(this);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // igralno polje
        int visina = (int)(getBounds().height / 13);
        int sirina = (int)(getBounds().width / 11);
        
        // polje - Max 13 visina
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

        g.setColor(BarveIgralcev.GetColor(master.cantstop.stanje.naVrsti));
        g.fillRect(10*sirina, 0, sirina, visina);
    }

    private void klik(int x, int y){
        int visina = (int)(getBounds().height / 13);
        int sirina = (int)(getBounds().width / 11);

        if(master.cantstop.vrzeneKocke == null){
            if((x <= 2*sirina) & (y <= visina)){
                try {
                    master.cantstop.Met();
                    repaint();
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
                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(master.cantstop.veljavneOdlocitve.isEmpty()){
            if((x > 2*sirina) & (x <= 4*sirina) & (y < visina)){
                try {
                    master.cantstop.zakljuci();
                    repaint();
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

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}