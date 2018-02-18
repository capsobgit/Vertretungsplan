package client.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.Naming;
import javax.swing.*;

import server.ServerTableModel;
import shared.Datum;
import client.control.TabController;
import client.control.HauptController;
import client.model.Bezeichnungen;
import client.model.Model;
import client.model.Point;

/**
 * Hauptprogramm zum Starten des RMI-Clients
 * und einrichten der GUI. Das Menufenster öffnet
 * sich beim anklicken einer Tabellen-Zelle, außer
 * bei denen, welche in der ersten Spalte liegen.
 * Der entsprechende MouseListener ist hier realisiert.
 * @author Bruno Sobral
 */
@SuppressWarnings("serial")
public class Mainframe extends JFrame
{
    public Mainframe()
    {
        super("Vertretungsplan");
        setLayout(null);
        //Fenster Merkmale
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Modell
        final Model model = new Model();
        //RMI-Objekte
        final ServerTableModel[] rmi = getRMI();
        //GuiKomponenten
        final StundenTabelleView stundenTabelle1
                = new StundenTabelleView(dim, 1);
        final StundenTabelleView stundenTabelle2
                = new StundenTabelleView(dim, 2);
        AufsichtsTabellenView aufsichtsTabelle
                = new AufsichtsTabellenView(model.getLehrerKrzl(), rmi[2], dim);
        //StatistikView
        StatistikDummyView stView = new StatistikDummyView();
        //Menu Gui mit eigenen Ereignisbehandlern
        MenuView menu = new MenuView(model);
        setJMenuBar(menu);
        //Rahmen für Stundentabellen
        JScrollPane scroll1 = new JScrollPane(stundenTabelle1);
        JScrollPane scroll2 = new JScrollPane(aufsichtsTabelle);
        JScrollPane scroll3 = new JScrollPane(stundenTabelle2);
        JScrollPane scroll4 = new JScrollPane(stView);
        //Abschicken Knöpfe
        JButton abschicken1 = new JButton(Bezeichnungen.Abschicken.getName());
        JButton abschicken2 = new JButton(Bezeichnungen.Abschicken.getName());
        abschicken1.setBounds(100, dim.height / 2 + 55, 150, 40);
        abschicken1.setBorder(BorderFactory.createLineBorder(Color.black));
        abschicken2.setBackground(Color.lightGray);
        abschicken2.setForeground(Color.blue);
        abschicken2.setBounds(100, dim.height / 2 + 55, 150, 40);
        abschicken2.setBorder(BorderFactory.createLineBorder(Color.black));
        //Kontroller und Listener
        HauptController tabellenCtr1 = new HauptController(
                model, null, stundenTabelle1, null, rmi[0]);
        HauptController tabellenCtr2 = new HauptController(
                model, null, stundenTabelle2, null, rmi[1]);
        abschicken1.addActionListener(tabellenCtr1);
        abschicken2.addActionListener(tabellenCtr2);
        //Beobachter
        model.addModelListener(stundenTabelle1);
        model.addModelListener(stundenTabelle2);
        model.addModelListener(aufsichtsTabelle);
        model.addModelListener(stView);
        //Datum
        Datum datumH = new Datum(1);
        JLabel datumLabelH = new JLabel(datumH.getDatum());
        Datum datumM = new Datum(2);
        JLabel datumLabelM = new JLabel(datumM.getDatum());
        //MouseListener an Stundentabelle
        stundenTabelle1.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JTable target = (JTable) e.getSource();
                int eventRow = target.getSelectedRow();
                int eventCol = target.getSelectedColumn();
                int x = e.getX();
                int y = e.getY();
                if (eventCol > 0)
                {
                    new TabellenSubFenster(model, stundenTabelle1,
                            new Point(eventRow, eventCol),
                            new Point(x, y), rmi[0]);
                }
            }
        });
        stundenTabelle2.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JTable target = (JTable) e.getSource();
                int eventRow = target.getSelectedRow();
                int eventCol = target.getSelectedColumn();
                int x = e.getX();
                int y = e.getY();
                if (eventCol > 0)
                {
                    new TabellenSubFenster(model, stundenTabelle2,
                            new Point(eventRow, eventCol),
                            new Point(x, y), rmi[1]);
                }
            }
        });
        //Komponenten positionieren
        scroll2.setBounds(100, dim.height / 2 + dim.height / 10,
                dim.width - 200, dim.height / 5);
        datumLabelH.setBounds(100, 0, 300, 40);
        datumLabelH.setFont(new Font("Serif", Font.BOLD, 18));
        datumLabelH.setForeground(Color.blue);
        datumLabelM.setBounds(100, 0, 300, 40);
        datumLabelM.setFont(new Font("Serif", Font.BOLD, 18));
        datumLabelM.setForeground(Color.darkGray);
        //TabbedPane
        JTabbedPane tabTabelle = new JTabbedPane();
        tabTabelle.addChangeListener(new TabController(abschicken1, abschicken2,
                datumLabelH, datumLabelM));
        tabTabelle.setBounds(0, 50, dim.width, dim.height / 2);
        //Komponenten zum Tabbedpane himzufügen
        tabTabelle.addTab(Bezeichnungen.Heute.getName(), scroll1);
        tabTabelle.addTab(Bezeichnungen.FolgenderTag.getName(), scroll3);
        tabTabelle.addTab(Bezeichnungen.Statistik.getName(), scroll4);
        //Komponenten Fenster zufügen/////////
        add(datumLabelH);
        add(datumLabelM);
        add(tabTabelle);
        add(abschicken1);
        add(abschicken2);
        add(scroll2);
        //Größe und Sichtbarkeit
        setSize(dim);
        setVisible(true);
    }
    //Entfernte Objekte allokieren 
    private ServerTableModel[] getRMI()
    {
        ServerTableModel[] stundenModel = new ServerTableModel[3];
        try
        {
            stundenModel[0]
                    = (ServerTableModel) Naming.lookup(
                            "rmi://localhost" + "/ServerTableHeute");
            stundenModel[1]
                    = (ServerTableModel) Naming.lookup(
                            "rmi://localhost" + "/ServerTableMorgen");
            stundenModel[2]
                    = (ServerTableModel) Naming.lookup(
                            "rmi://localhost" + "/Aufsicht");
            System.out.println("Server erreicht und Objekte erhalten..[]");
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "RMI-Server nicht gestartet!", 
                    "Keine Verbindung", JOptionPane.WARNING_MESSAGE);
            System.out.println("Bitte vorher Server starten!");
            e.printStackTrace();
        }
        return stundenModel;
    }

    public static void main(String[] args)
    {
        new Mainframe();
    }
}
