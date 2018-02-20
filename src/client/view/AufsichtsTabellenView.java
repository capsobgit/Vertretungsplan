package client.view;

import java.awt.Dimension;
import java.awt.Font;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import shared.SmallRenderer;
import client.control.AufsichtsTabellenController;
import client.model.Model;
import server.IServerTableModel;
import client.model.IModelListener;

/**
 * Eine View für die Aufsichtstabelle.
 *
 * @author sobdaro
 */

public class AufsichtsTabellenView extends JTable implements IModelListener, Serializable
{

    private static final long serialVersionUID = -1593873850009952750L;
    private ArrayList<String> kürzel;
    private JComboBox<Object> combo;

    public AufsichtsTabellenView(ArrayList<String> list, IServerTableModel remote,
            final Dimension dim)
    {
        setDefaultRenderer(Object.class, new SmallRenderer());
        kürzel = new ArrayList<>(64);
        combo = new JComboBox<>(
                new DefaultComboBoxModel<Object>(list.toArray()));
        final String[] AufsichtsTabellenHeader =
        {
            "Superfrüh", "", "", "Altbau", "Neubau1", "Neubau2", "", ""
        };

        String[][] aufsichtsTabellenStruktur =
        {
            {
                "Hof früh:", "", "Gebäude Früh:", "", "", "", "GTS Pause:", ""
            },
            {
                "Hof I:", "", "Gebäude I:", "", "", "", "GTS Bus:", ""
            },
            {
                "Hof II:", "", "Gebäude II:", "", "", "", ""
            },
            {
                "Bus", "", "Gebäude II:", "", "", "", ""
            },
            {
                "", "", "", "", "", "", ""
            },
            {
                "7. Std. (MiPa) Mensa", "", "", "", "", "", ""
            }
        };

        final DefaultTableModel model = new DefaultTableModel(
                aufsichtsTabellenStruktur, AufsichtsTabellenHeader);
        SwingUtilities.invokeLater(() ->
        {
            setModel(model);
            for (int i = 0; i < AufsichtsTabellenHeader.length; i++)
            {
                if (i == 0 || i == 2 || i == 6)
                {
                    continue;
                }

                TableColumn column = getColumnModel().getColumn(i);
                column.setCellEditor(new DefaultCellEditor(combo));
            }
            setFont(new Font("DejaVu Sans", Font.BOLD, 17));
            getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 18));
            setRowHeight(dim.height / 33);
        });
        model.addTableModelListener(new AufsichtsTabellenController(remote, this,
                AufsichtsTabellenHeader));
    }

    @Override
    public void modelChanged(Model model)
    {
        kürzel = model.getLehrerKrzl();
        combo.setModel(new DefaultComboBoxModel<>(kürzel.toArray()));
    }
}
