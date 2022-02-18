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

    private JMenuBar mainMenu;
    private JMenu novaIgra;
    private JMenuItem novaIgra1;
    private JMenuItem novaIgra2;
    private JMenuItem novaIgra3;
    private JMenuItem novaIgra4;
    private JMenuItem novaIgra5;
    private JMenuItem novaIgra6;

    private Platno platno;

    private JLabel status;

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

        novaIgra1 = new JMenuItem("1");
        novaIgra.add(novaIgra1);
        novaIgra1.addActionListener(this);

        novaIgra2 = new JMenuItem("2");
        novaIgra.add(novaIgra2);
        novaIgra2.addActionListener(this);

        novaIgra3 = new JMenuItem("3");
        novaIgra.add(novaIgra3);
        novaIgra3.addActionListener(this);

        novaIgra4 = new JMenuItem("4");
        novaIgra.add(novaIgra4);
        novaIgra4.addActionListener(this);

        novaIgra5 = new JMenuItem("5");
        novaIgra.add(novaIgra5);
        novaIgra5.addActionListener(this);

        novaIgra6 = new JMenuItem("6");
        novaIgra.add(novaIgra6);
        novaIgra6.addActionListener(this);

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
            if (e.getSource() == novaIgra1) {
                novaIgra(1);
            }
            if (e.getSource() == novaIgra2) {
                novaIgra(2);
            }
            if (e.getSource() == novaIgra3) {
                novaIgra(3);
            }
            if (e.getSource() == novaIgra4) {
                novaIgra(4);
            }
            if (e.getSource() == novaIgra5) {
                novaIgra(5);
            }
            if (e.getSource() == novaIgra6) {
                novaIgra(6);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}