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

    private DiceContainer kocke;

    public Platno(Okno master) {
        super();

        try {
            kocke = new DiceContainer();
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
        int visina = (int) (getBounds().height / 13);
        int sirina = (int) (getBounds().width / 11);

        g.setFont(new Font(g.getFont().getName(), g.getFont().getStyle(), (int) (visina / 3)));

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
                        g.drawString(String.valueOf(polje), (polje - 2) * sirina + (int) (2 * sirina / 5),
                                (12 - visoko) * visina + 2 * (int) (visina / 3));
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.drawRect((polje - 2) * sirina, (12 - visoko) * visina, sirina - 1, visina - 1);
                }
            } catch (

            Exception e) {
                e.printStackTrace();
            }
        }

        // Igralci
        int sirinaIgralca = (int) ((sirina - 2) / master.cantstop.stanje.igralcev);
        for (int igralec = 0; igralec < master.cantstop.stanje.igralcev; igralec++) {
            // polje - Max 13 visina
            StanjeIgralca stanjeI = master.cantstop.stanje.Get(igralec);
            for (int polje = 0; polje < 11; polje++) {
                int visoko = stanjeI.visine[polje];
                if (visoko > 0) {
                    g.setColor(BarveIgralcev.GetColor(igralec));
                    g.fillRect(polje * sirina + (sirinaIgralca * igralec) + 1, (13 - visoko) * visina + 1,
                            sirinaIgralca, visina - 2);
                }
            }
        }

        // zasedenost
        for (int polje : master.cantstop.stanje.zasedeni.keySet()) {
            g.setColor(BarveIgralcev.GetColor(master.cantstop.stanje.zasedeni.get(polje)));
            try {
                int visinastolpca = MaxVisine.MaxVisina(polje);
                g.fillRect((polje - 2) * sirina + (int) (sirina / 4),
                        (13 - visinastolpca) * visina + (int) (visina / 4), sirina - 2 * (int) (sirina / 4),
                        visinastolpca * visina - 2 * (int) (visina / 4));
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
                    g.fillOval(polje * sirina + (int) (sirina / 3), (13 - zacetnaVisina) * visina + (int) (visina / 3),
                            sirina - 2 * (int) (sirina / 3), visina - 2 * (int) (visina / 3));
                    g.setColor(Color.BLACK);
                    g.drawOval(polje * sirina + (int) (sirina / 3), (13 - zacetnaVisina) * visina + (int) (visina / 3),
                            sirina - 2 * (int) (sirina / 3), visina - 2 * (int) (visina / 3));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            polje++;
        }

        if (master.cantstop.vrzeneKocke == null) {
            g.setColor(Color.WHITE);
            g.drawRect(0, 0, 2 * sirina, visina - 1);
            g.drawString("Met!", (int) (sirina / 3), 2 * (int) (visina / 3));
            g.drawRect(0, visina, 2 * sirina, 4 * visina - 1);
        } else {
            // 4*sirina - 2*visina

            int stranica = Math.min(sirina, 2 * visina);
            int dx = (4 * sirina - 4 * stranica) / 2;
            int dy = (2 * visina - stranica) / 2;

            g.drawImage(kocke.slikaKocke(master.cantstop.vrzeneKocke[0]), dx, dy, stranica, stranica, null);
            g.drawImage(kocke.slikaKocke(master.cantstop.vrzeneKocke[1]), dx + stranica, dy, stranica, stranica, null);
            g.drawImage(kocke.slikaKocke(master.cantstop.vrzeneKocke[2]), dx + 2 * stranica, dy, stranica, stranica,
                    null);
            g.drawImage(kocke.slikaKocke(master.cantstop.vrzeneKocke[3]), dx + 3 * stranica, dy, stranica, stranica,
                    null);

            try {
                // 1. odlocitev
                int[] cifreOdlocitve = master.cantstop.cifreOdlocitve(0);
                if (!master.cantstop.lahkoIgra(cifreOdlocitve[0])) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.GREEN);
                }
                g.drawRect(0, 2 * visina, sirina - 1, visina - 1);
                g.drawString(String.valueOf(cifreOdlocitve[0]), (int) (sirina / 3),
                        2 * visina + 2 * (int) (visina / 3));
                if (!master.cantstop.lahkoIgra(cifreOdlocitve[1])) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.GREEN);
                }
                g.drawRect(sirina, 2 * visina, sirina - 1, visina - 1);
                g.drawString(String.valueOf(cifreOdlocitve[1]), sirina + (int) (sirina / 3),
                        2 * visina + 2 * (int) (visina / 3));

                // 2. odlocitev
                cifreOdlocitve = master.cantstop.cifreOdlocitve(1);
                if (!master.cantstop.lahkoIgra(cifreOdlocitve[0])) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.GREEN);
                }
                g.drawRect(0, 3 * visina, sirina - 1, visina - 1);
                g.drawString(String.valueOf(cifreOdlocitve[0]), (int) (sirina / 3),
                        3 * visina + 2 * (int) (visina / 3));
                if (!master.cantstop.lahkoIgra(cifreOdlocitve[1])) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.GREEN);
                }
                g.drawRect(sirina, 3 * visina, sirina - 1, visina - 1);
                g.drawString(String.valueOf(cifreOdlocitve[1]), sirina + (int) (sirina / 3),
                        3 * visina + 2 * (int) (visina / 3));

                // 3. odlocitev
                cifreOdlocitve = master.cantstop.cifreOdlocitve(2);
                if (!master.cantstop.lahkoIgra(cifreOdlocitve[0])) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.GREEN);
                }
                g.drawRect(0, 4 * visina, sirina - 1, visina - 1);
                g.drawString(String.valueOf(cifreOdlocitve[0]), (int) (sirina / 3),
                        4 * visina + 2 * (int) (visina / 3));
                if (!master.cantstop.lahkoIgra(cifreOdlocitve[1])) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.GREEN);
                }
                g.drawRect(sirina, 4 * visina, sirina - 1, visina - 1);
                g.drawString(String.valueOf(cifreOdlocitve[1]), sirina + (int) (sirina / 3),
                        4 * visina + 2 * (int) (visina / 3));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (master.cantstop.veljavneOdlocitve.isEmpty()) {
            g.setColor(Color.WHITE);
            g.drawRect(0, 5 * visina, 2 * sirina, visina);
            g.drawString("Zakljuci!", (int) (sirina / 3), 5 * visina + 2 * (int) (visina / 3));
        }

        g.setColor(Color.WHITE);
        String igralecLbl;
        int stIgr = master.cantstop.stanje.igralcev;
        if (stIgr == 1) {
            igralecLbl = " igralec";
        } else if (stIgr == 2) {
            igralecLbl = " igralca";
        } else if (stIgr < 5) {
            igralecLbl = " igralci";
        } else {
            igralecLbl = " igralcev";
        }
        g.drawString(String.valueOf(master.cantstop.stanje.igralcev) + igralecLbl, 10 * sirina, 2 * (int) (visina / 2));
    }

    private void klik(int x, int y) {
        int visina = (int) (getBounds().height / 13);
        int sirina = (int) (getBounds().width / 11);

        if (master.cantstop.vrzeneKocke == null) {
            if ((x <= 2 * sirina) & (y <= visina)) {
                try {
                    master.cantstop.Met();
                    master.osveziUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            if ((x <= 2 * sirina) & (y >= 2 * visina) & (y < 5 * visina)) {
                boolean levo = (x <= sirina);
                int opcija = (int) ((y - 2 * visina) / visina);

                try {
                    master.cantstop.odigraj(opcija, levo);
                    master.osveziUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (master.cantstop.veljavneOdlocitve.isEmpty()) {
            if ((x <= 2 * sirina) & (y >= 5 * visina) & (y < 6 * visina)) {
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