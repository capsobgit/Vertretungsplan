package shared;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableCellRenderer;

/**
 * Renderer für Haupttabelle
 *
 * @author sobdaro
 */
@SuppressWarnings("serial")
public class BigRenderer implements TableCellRenderer
{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
    {
        JTextPane textPane = null;

        CellData data = (CellData) table.getModel().getValueAt(row, column);
         //StundenSpalte Anfang
        for (int i = 0; i <= 8; i++)
        {
            if (row == i && column == 0)
            {
                textPane = new TextPaneView(i + 1);
                return textPane;
            }
        }
        //Restliche Spalten
        if (data != null && data.isEmpty())
        {
            textPane = new TextPaneView(data.getErsatz(), Color.yellow,
                    table.getRowHeight(0));
        } else
        {
            textPane = new TextPaneView(data.getFehlend()
                    == null ? "[..]" : data.getFehlend(), data.getFach(),
                    data.getErsatz(), data.isPlusStunde(),
                    table.getRowHeight(0));
        }
        return textPane;
    }
}
