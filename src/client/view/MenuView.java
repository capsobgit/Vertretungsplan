package client.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import client.control.FaecherListeController;
import client.control.KlassenListeController;
import client.control.KuerzlController;
import client.model.Bezeichnungen;
import client.model.Model;

/**
 * Programm zur Ausgestaltung der Menubar des Hauptfensters Enthält eine eigene
 * Ereignisbehandlung
 *
 * @author Bruno Sobral
 */
@SuppressWarnings("serial")
public class MenuView extends JMenuBar
        implements ActionListener
{

    private Model model;

    public MenuView(Model model)
    {
        this.model = model;
        setBackground(Color.blue);
        //OberMenus
        JMenu fileMenu = new JMenu("Datei");
        JMenu configMenu = new JMenu("Einstellungen");
        //UnterMenus
        JMenu drucken = new JMenu("Drucken");
        JMenu laden = new JMenu("Tabelle laden");
        //Menuelemente
        JMenuItem lehrerHinzufügen
                = new JMenuItem(Bezeichnungen.LehrerHinzufuegen.getName());
        JMenuItem klassenHinzufügen
                = new JMenuItem(Bezeichnungen.KlasseHinzufuegen.getName());
        JMenuItem fachHinzufügen
                = new JMenuItem(Bezeichnungen.FaecherHinzufuegen.getName());
        JMenuItem tabelle = new JMenuItem(Bezeichnungen.Tabelle.getName());
        JMenuItem statistik = new JMenuItem(Bezeichnungen.Statistik.getName());
        JMenuItem speichern = new JMenuItem(Bezeichnungen.Speichern.getName());
        JMenuItem heute = new JMenuItem(Bezeichnungen.FuerHeute.getName());
        JMenuItem danach = new JMenuItem(Bezeichnungen.FolgenderTag.getName());
        //Ereignisbehandler
        klassenHinzufügen.addActionListener(this);
        lehrerHinzufügen.addActionListener(this);
        fachHinzufügen.addActionListener(this);
        tabelle.addActionListener(this);
        statistik.addActionListener(this);
        speichern.addActionListener(this);
        heute.addActionListener(this);
        danach.addActionListener(this);

        //Menuelemente hinzufügen 
        fileMenu.add(speichern);
        fileMenu.add(laden);
        drucken.add(tabelle);
        drucken.add(statistik);
        laden.add(heute);
        laden.add(danach);
        configMenu.add(lehrerHinzufügen);
        configMenu.add(klassenHinzufügen);
        configMenu.add(fachHinzufügen);

        //Ein Menu kann ein anderes Menu aufnehmen
        fileMenu.add(drucken);
        //In die Menubar setzen
        add(fileMenu);
        add(configMenu);
    }
    
    /**
     * Diese Ereignisbhandlung tritt dann ein, wenn ein Menu-Element ausgewählt
     * wird. Es wird ein annonymes Unterfenster-Objekt vom Typ MenuSubfenster
     * erzeugt, welches das Action-Kommando für weitere Verarbeitung mitbekommt.
     * Außerdem werden entsprechende neu erzeugte Controller mitgegeben. Das
     * Subfenster-Objekt wird über die Modell-Referenz als Beobachter
     * angemeldet, und über den WindowListener bei Fensterscchliessung wieder
     * abgemeldet.
     * 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String tmp = e.getActionCommand();
        if (tmp.equals(Bezeichnungen.Speichern.getName()))
        {
            model.putTabellenModel();
            JOptionPane.showMessageDialog(null, "Heutige Tabelle gespeichert");
            return;
        }
        if (tmp.equals(Bezeichnungen.FuerHeute.getName()))
        {
            model.initTabellenModel(1);
            return;
        }
        if (tmp.equals(Bezeichnungen.FolgenderTag.getName()))
        {
            model.initTabellenModel(2);
            return;
        }
        if (tmp.equals(Bezeichnungen.LehrerHinzufuegen.getName())
                || tmp.equals(Bezeichnungen.FaecherHinzufuegen.getName())
                || tmp.equals(Bezeichnungen.KlasseHinzufuegen.getName()))
        {
            KuerzlController kuerzl
                    = new KuerzlController(model);
            KlassenListeController klasse
                    = new KlassenListeController(model);
            FaecherListeController fach
                    = new FaecherListeController(model);
            final MenuSubFenster subfenster
                    = new MenuSubFenster(kuerzl, klasse, fach);
            model.addModelListener(subfenster);

            //Subfenster bei Schließung abmelden
            subfenster.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    //wieder aus der "Observer-Liste" streichen
                    client.view.MenuView.this.model.removeModelListener(subfenster);
                }
            });
        }
    }
}
