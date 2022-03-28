package UI;

import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Logika.Igra;

@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {
    protected Igra cantstop;

    // #region Menu
    private JMenuBar mainMenu;

    private JMenu novaIgra;
    private JMenuItem[] novaIgraN;

    private JMenu neomejenoZmag;

    private JMenuItem[] neoZmJaNe;
    // #endregion Menu

    private Platno platno;

    private JLabel status;

    private static final int maxIgralcev = 6;

    public Okno() throws Exception {
        super();

        setTitle("Can't Stop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // BufferedImage iconImage = ImageIO.read(new File(".\\Images\\diceIcon.png"));

        BufferedImage iconImage = ImageIO.read(getClass().getClassLoader().getResource("diceIcon.png"));
        setIconImage(iconImage);

        // #region Menu
        mainMenu = new JMenuBar();

        novaIgra = new JMenu("Nova igra");
        mainMenu.add(novaIgra);

        novaIgraN = new JMenuItem[maxIgralcev];
        for (int i = 0; i < maxIgralcev; i++) {
            novaIgraN[i] = new JMenuItem(String.valueOf(i + 1));
            novaIgra.add(novaIgraN[i]);
            novaIgraN[i].addActionListener(this);
        }

        neomejenoZmag = new JMenu("Neomejeno zmag");
        mainMenu.add(neomejenoZmag);

        neoZmJaNe = new JMenuItem[2];
        for (int i = 0; i < 2; i++) {
            neoZmJaNe[i] = new JMenuItem(((i == 0) ? "Ja" : "Ne"));
            neomejenoZmag.add(neoZmJaNe[i]);
            neoZmJaNe[i].addActionListener(this);
        }

        this.setJMenuBar(mainMenu);
        // #endregion Menu

        // Platno
        platno = new Platno(this);
        GridBagConstraints platnoLayout = new GridBagConstraints();
        platnoLayout.gridx = 0;
        platnoLayout.gridy = 0;
        platnoLayout.weightx = 1;
        platnoLayout.weighty = 1;
        platnoLayout.fill = GridBagConstraints.BOTH;
        getContentPane().add(platno, platnoLayout);

        // Status
        status = new JLabel();
        status.setFont(new Font(status.getFont().getName(), status.getFont().getStyle(), 20));
        status.setBackground(Color.BLACK);
        status.setOpaque(true);
        GridBagConstraints statusLayout = new GridBagConstraints();
        statusLayout.gridx = 0;
        statusLayout.gridy = 1;
        statusLayout.anchor = GridBagConstraints.CENTER;
        statusLayout.fill = GridBagConstraints.BOTH;
        getContentPane().add(status, statusLayout);

        novaIgra(4);
    }

    private void novaIgra(int igralcev) throws Exception {
        cantstop = new Igra(igralcev);
        osveziUI();
    }

    private void setNeomejenoZmag(boolean ja) {
        if (cantstop.getNeomejenoZmag() != ja) {
            cantstop.setNeomejenoZmag(ja);
            osveziUI();
        }
    }

    protected void osveziUI() {
        setStatus();
        platno.repaint();
    }

    private void setStatus() {
        if (cantstop == null) {
            status.setForeground(Color.WHITE);
            status.setText("Igra ni v teku");
        } else {
            status.setForeground(BarveIgralcev.GetColor(cantstop.stanje.naVrsti));
            switch (cantstop.stanje.stanjeIgre) {
                case VTEKU:
                    status.setText("Na vrsti je " + String.valueOf(cantstop.stanje.naVrsti + 1) + ". igralec");
                    break;
                case ZMAGA1:
                    status.setForeground(BarveIgralcev.GetColor(0));
                    status.setText("Zmagal je 1. igralec!");
                    break;
                case ZMAGA2:
                    status.setForeground(BarveIgralcev.GetColor(1));
                    status.setText("Zmagal je 2. igralec!");
                    break;
                case ZMAGA3:
                    status.setForeground(BarveIgralcev.GetColor(2));
                    status.setText("Zmagal je 3. igralec!");
                    break;
                case ZMAGA4:
                    status.setForeground(BarveIgralcev.GetColor(3));
                    status.setText("Zmagal je 4. igralec!");
                    break;
                case ZMAGA5:
                    status.setForeground(BarveIgralcev.GetColor(4));
                    status.setText("Zmagal je 5. igralec!");
                    break;
                case ZMAGA6:
                    status.setForeground(BarveIgralcev.GetColor(5));
                    status.setText("Zmagal je 6. igralec!");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            for (int i = 0; i < maxIgralcev; i++) {
                if (e.getSource() == novaIgraN[i]) {
                    novaIgra(i + 1);
                }
            }
            if (e.getSource() == neoZmJaNe[0]) {
                setNeomejenoZmag(true);
            }
            if (e.getSource() == neoZmJaNe[1]) {
                setNeomejenoZmag(false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}