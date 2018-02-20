package shared;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * @author sobdaro
 */

@SuppressWarnings("serial")
public class SmallRenderer extends DefaultTableCellRenderer
{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        if (column == 0 || column == 2 || column == 6)
        {
            c.setForeground(Color.black);
        } else
        {
            c.setForeground(Color.blue);
            c.setFont(new Font("Droid Sans", Font.BOLD, 22));
        }

        return c;
    }
}
