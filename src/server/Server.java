package server;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import javax.swing.*;
import shared.Datum;

/**
 * RMI-Server lädt Stundenplan-Tabellen in die Registry
 *
 * @author Bruno Sobral
 */
@SuppressWarnings("serial")
public class Server extends JFrame
{

    final static private String tabelle1 = "ServerTableHeute";
    final static private String tabelle2 = "ServerTableMorgen";
    final static private String tabelle3 = "Aufsicht";

    public Server(String name, ServerStundenTabelleView servertableH,
            ServerStundenTabelleView servertableM, ServerAufsichtsTabellenView aufview
    /*, ServerAufsichtsTabellenView aufsMorgen*/)
    {
        super(name);
        setLayout(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JScrollPane scroll1 = new JScrollPane(servertableH);
        scroll1.setBorder(BorderFactory.createTitledBorder("Heute"));
        JScrollPane scroll3 = new JScrollPane(servertableM);
        scroll1.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
        scroll3.setBorder(BorderFactory.createLineBorder(Color.darkGray, 5));
        servertableM.setBackground(Color.LIGHT_GRAY);
        JScrollPane scroll2 = new JScrollPane(aufview);
        // Datum
        Datum datumH = new Datum(1);
        Datum datumM = new Datum(2);
        JLabel datumLabelH = new JLabel(datumH.getDatum());
        JLabel datumLabelM = new JLabel(datumM.getDatum());
        datumLabelH.setBounds(100, 0, 400, 40);
        datumLabelH.setFont(new Font("Serif", Font.BOLD, 20));
        datumLabelH.setForeground(Color.blue);
        datumLabelM.setBounds((dim.width / 2) + (dim.width / 4), 0, 400, 40);
        datumLabelM.setFont(new Font("Serif", Font.BOLD, 20));
        datumLabelM.setForeground(Color.black);
        //Postitionierung
        scroll1.setBounds(0, 50, dim.width / 2, dim.height / 2);
        scroll3.setBounds(dim.width / 2, 50, dim.width / 2, dim.height / 2);
        scroll2.setBounds(100, dim.height / 2 + dim.height / 8, dim.width - 200, 209);
        // Komponenten hinzufügen
        add(datumLabelH);
        add(datumLabelM);
        add(scroll1);
        add(scroll3);
        add(scroll2);
        setSize(dim);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        // System.setSecurityManager(new RMISecurityManager());
        ServerStundenTabelleView servertableHeute = new ServerStundenTabelleView();
        ServerStundenTabelleView servertableMorgen = new ServerStundenTabelleView();
        ServerAufsichtsTabellenView aufView = new ServerAufsichtsTabellenView();

        new Server("Vertretungsplan", servertableHeute, servertableMorgen, aufView);
        try
        {
            ServerTableModelImpl impl1 = new ServerTableModelImpl(servertableHeute);
            ServerTableModelImpl impl2 = new ServerTableModelImpl(servertableMorgen);
            ServerTableModelImpl impl3 = new ServerTableModelImpl(aufView);

            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(tabelle1, impl1);
            registry.rebind(tabelle2, impl2);
            registry.rebind(tabelle3, impl3);
            System.out
                    .println("RMIServer mit folgenden Objekten gestartet: ..\n"
                            + Arrays.toString(registry.list()));
        } catch (RemoteException rem)
        {
            rem.printStackTrace();
            System.out.println(rem.getMessage());
        }
    }
}
