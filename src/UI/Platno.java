package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JPanel;

import Logika.MaxVisine;
import Logika.StanjeIgralca;

@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener {

    public Okno master;

    public Platno(Okno master) {
        super();

        try {
            DiceContainer.Initialize(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBackground(Color.BLACK);
        addMouseListener(this);
        this.master = master;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(11 * 100, 13 * 60);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // igralno polje
        int visinaPolja = (int) (getBounds().height / 13);
        int sirinaPolja = (int) (getBounds().width / 11);
        PositionLoader.setSirinaVisina(sirinaPolja, visinaPolja);

        g.setFont(new Font(g.getFont().getName(), g.getFont().getStyle(), (int) (visinaPolja / 3)));

        // polje - Max 13 visina
        for (int polje = 2; polje < 13; polje++) {
            int maxVisina;
            try {
                maxVisina = MaxVisine.MaxVisina(polje);
                for (int visoko = 0; visoko < maxVisina; visoko++) {
                    if (visoko == maxVisina - 1) {
                        if (master.cantstop.cifreVIgri.contains(polje)) {
                            if (master.cantstop.stanje.Get(master.cantstop.stanje.naVrsti).visine[polje
                                    - 2] >= maxVisina) {
                                g.setColor(Color.RED);
                            } else {
                                g.setColor(Color.GREEN);
                            }
                        } else {
                            if ((master.cantstop.cifreVIgri.size() >= 3)
                                    | (master.cantstop.stanje.zasedeni.containsKey(polje))) {
                                g.setColor(Color.RED);
                            } else {
                                g.setColor(Color.YELLOW);
                            }
                        }
                        PositionLoader.napisiPolje(g, String.valueOf(polje), polje, visoko);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    PositionLoader.izrisiPolje(g, polje, visoko);
                }
            } catch (

            Exception e) {
                e.printStackTrace();
            }
        }

        // Igralci
        int sirinaIgralca = (int) ((sirinaPolja - 2) / master.cantstop.stanje.igralcev);
        PositionLoader.setSirinaIgralca(sirinaIgralca);

        for (int igralec = 0; igralec < master.cantstop.stanje.igralcev; igralec++) {
            // polje - Max 13 visina
            StanjeIgralca stanjeI = master.cantstop.stanje.Get(igralec);
            for (int polje = 2; polje < 13; polje++) {
                int visoko = stanjeI.visine[polje - 2];
                if (visoko > 0) {
                    g.setColor(BarveIgralcev.GetColor(igralec));
                    PositionLoader.izrisiIgralca(g, polje, visoko, igralec);
                }
            }
        }

        // zasedenost
        for (int polje : master.cantstop.stanje.zasedeni.keySet()) {
            g.setColor(BarveIgralcev.GetColor(master.cantstop.stanje.zasedeni.get(polje)));
            try {
                int visinastolpca = MaxVisine.MaxVisina(polje);
                PositionLoader.izrisiZasedenost(g, polje, visinastolpca);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // iz kje je zacel
        int polje = 0;
        Color barvaIgralca = BarveIgralcev.GetColor(master.cantstop.stanje.naVrsti);
        for (int zacetnaVisina : master.cantstop.prejsnjeStanjeIgralca.visine) {
            try {
                if ((zacetnaVisina > 0) & (zacetnaVisina < MaxVisine.MaxVisina(polje + 2))) {
                    g.setColor(barvaIgralca);
                    PositionLoader.izrisiZacetek(g, polje, zacetnaVisina);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            polje++;
        }

        if (master.cantstop.vrzeneKocke == null) {
            g.setColor(Color.WHITE);
            PositionLoader.izrisiMet(g);
            PositionLoader.izrisiPodMet(g);
        } else {
            // 4*sirina - 2*visina
            int stranica = Math.min(sirinaPolja, 2 * visinaPolja);
            int dx = (4 * sirinaPolja - 4 * stranica) / 2;
            int dy = (2 * visinaPolja - stranica) / 2;
            PositionLoader.setMereKock(stranica, dx, dy);

            PositionLoader.izrisiKocko(g, 0, master.cantstop.vrzeneKocke[0]);
            PositionLoader.izrisiKocko(g, 1, master.cantstop.vrzeneKocke[1]);
            PositionLoader.izrisiKocko(g, 2, master.cantstop.vrzeneKocke[2]);
            PositionLoader.izrisiKocko(g, 3, master.cantstop.vrzeneKocke[3]);

            try {
                izrisiOdlocitev(g, 0);
                izrisiOdlocitev(g, 1);
                izrisiOdlocitev(g, 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (master.cantstop.veljavneOdlocitve.isEmpty()) {
            g.setColor(Color.WHITE);
            PositionLoader.izrisiZakljucek(g);
        }

        // #region Info
        g.setColor(Color.WHITE);
        String igralecLbl = String.valueOf(master.cantstop.stanje.igralcev);
        int stIgr = master.cantstop.stanje.igralcev;
        if (stIgr == 1) {
            igralecLbl += " igralec";
        } else if (stIgr == 2) {
            igralecLbl += " igralca";
        } else if (stIgr < 5) {
            igralecLbl += " igralci";
        } else {
            igralecLbl += " igralcev";
        }
        g.drawString(igralecLbl, 10 * sirinaPolja,
                2 * (int) (visinaPolja / 3));

        String neomLbl;
        if (master.cantstop.getNeomejenoZmag()) {
            neomLbl = "Neomejeno zmag";
            g.setColor(Color.GREEN);
        } else {
            neomLbl = "3 zasedena polja";
        }
        g.drawString(neomLbl, 10 * sirinaPolja, visinaPolja + 2 * (int) (visinaPolja / 3));
        // #endregion Info
    }

    private void izrisiOdlocitev(Graphics g, int o) throws Exception {
        int[] cifreOdlocitve = master.cantstop.cifreOdlocitve(o);
        if (!master.cantstop.lahkoIgra(cifreOdlocitve[0])) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.GREEN);
        }
        PositionLoader.izrisiOdlocitev(g, o, true, cifreOdlocitve[0]);

        if (!master.cantstop.lahkoIgra(cifreOdlocitve[1])) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.GREEN);
        }
        PositionLoader.izrisiOdlocitev(g, o, false, cifreOdlocitve[1]);
    }

    private void klik(int x, int y) {
        if (master.cantstop.vrzeneKocke == null) {
            if (PositionLoader.klikMet(x, y)) {
                try {
                    master.cantstop.Met();
                    master.osveziUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (int opcija = 0; opcija < 3; opcija++) {
                for (Boolean levo : new boolean[] { false, true }) {
                    if (PositionLoader.klikOdlocitev(x, y, opcija, levo)) {
                        try {
                            master.cantstop.odigraj(opcija, levo);
                            master.osveziUI();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        if (master.cantstop.veljavneOdlocitve.isEmpty()) {
            if (PositionLoader.klikZakljucek(x, y)) {
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