package client.control;

import client.model.Bezeichnungen;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import client.model.Model;
import client.model.Point;
import client.view.StundenTabelleView;
import java.rmi.RemoteException;
import shared.CellData;
import server.IServerTableModel;

/**
 * Registriert Events am Hauptfenster
 *
 * @author sobdaro
 */
public class HauptController implements ActionListener
{

    private Model model;
    private Point point;
    private StundenTabelleView table;
    private String[] comboNames;
    private String krank, vertr, fach;
    private String manuelText;
    boolean manuel;
    boolean plusstunde;
    private JTextField field;
    private IServerTableModel remote;

    public HauptController(Model model, Point point,
            StundenTabelleView table, String[] comboNames,
            IServerTableModel remote)
    {
        this.model = model;
        this.point = point;
        this.table = table;
        this.comboNames = comboNames;
        this.remote = remote;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //Events von Subfenster
        if (e.getSource() instanceof JComboBox<?>)
        {
            JComboBox<?> cbbox = (JComboBox<?>) e.getSource();
            if (cbbox.getName().equals(comboNames[0]))
            {
                krank = (String) cbbox.getSelectedItem();
            } else if (cbbox.getName().equals(comboNames[1]))
            {
                fach = (String) cbbox.getSelectedItem();
            } else if (cbbox.getName().equals(comboNames[2]))
            {
                vertr = (String) cbbox.getSelectedItem();
            }
        } else if (e.getSource() instanceof JCheckBox)
        {
            plusstunde = !plusstunde;
        } else if (e.getSource() instanceof JTextField)
        {
            field = (JTextField) e.getSource();
            manuelText = field.getText();
            field.setBackground(Color.lightGray);
            manuel = true;
        }
        //Events zum aktualisieren des Tabellenmodells
        if (e.getSource() instanceof JButton)
        {
            if (e.getActionCommand().equals(Bezeichnungen.Uebertragen.getName()))
            {
                if (manuel)
                {
                    CellData data = new CellData(false, "", "", manuelText, true);
                    model.insertData(data, point.getEventTableRow(), point.getEventTableCol(),
                            table.getTag());
                    manuel = false;
                    field.setBackground(Color.white);
                    field.setText("");
                } else
                {
                    CellData data = new CellData(plusstunde, krank, fach, vertr, false);
                    model.insertData(data, point.getEventTableRow(), point.getEventTableCol(),
                            table.getTag());
                }
            }
        }
        //Tabellenmodell wird mit neuen Daten aktualisiert
        final DefaultTableModel tb = new DefaultTableModel(
                model.getDataMatrix(table.getTag()), model.getKlassenHeader().toArray());
        SwingUtilities.invokeLater(() ->
        {
            table.setModel(tb);
            tb.fireTableDataChanged();
            table.getColumnModel().getColumn(0).setPreferredWidth(70);
            table.getColumnModel().getColumn(0).setPreferredWidth(20);
        });
        //Event zum Abschicken an den Server
        if (e.getSource() instanceof JButton)
        {
            if (e.getActionCommand().equals(Bezeichnungen.Abschicken.getName())
                    || e.getActionCommand().equals("abgeschickt!"))
            {
                JButton b = (JButton) e.getSource();
                try
                {
                    remote.getRemoteModel(tb);
                    b.setForeground(Color.red);
                    b.setText("abgeschickt!");
                } catch (RemoteException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }
}
