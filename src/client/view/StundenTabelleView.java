package client.view;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import client.model.*;
import shared.*;

/**
 *
 * @author sobdaro
 */
@SuppressWarnings("serial")
public class StundenTabelleView extends JTable implements IModelListener
{

    private int tag;

    /**
     *
     * @param dim
     * @param tag
     */
    public StundenTabelleView(Dimension dim, int tag)
    {
        this.tag = tag;
        setDefaultRenderer(Object.class, new BigRenderer());
        setFillsViewportHeight(true);
        setRowHeight(dim.height / 20 + 1);
    }

    /**
     *
     * @return tag
     */
    public int getTag()
    {
        return tag;
    }

    /**
     * Hier erhält diese Tabelle die Referenz auf das Model, wenn sich dieses
     * ändert
     *
     * @param model
     */
    @Override
    public void modelChanged(Model model)
    {
        final CellData[][] data = model.getDataMatrix(tag);
        ArrayList<String> tmpList = model.getKlassenHeader();
        final String[] header = new String[tmpList.size()];
        for (int i = 0; i < header.length; i++)
        {
            header[i] = tmpList.get(i);
        }
        //Tabellenmodel wird mit neuen Daten aktualisiert
        SwingUtilities.invokeLater(() ->
        {
            DefaultTableModel tb
                    = new DefaultTableModel(data, header)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };
            client.view.StundenTabelleView.this.setModel(tb);
            //Hat Einfluss auf die erste Spalte
            if (getColumnCount() > 0)
            {
                getColumnModel().getColumn(0).setPreferredWidth(20);
            }
        });
    }
}
