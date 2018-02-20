package server;

import java.awt.Font;
import javax.swing.JTable;
import shared.BigRenderer;

/**
 * @author sobdaro
 * @see client.StudentabellenView
 */
@SuppressWarnings("serial")
public class ServerStundenTabelleView extends JTable
{
    public ServerStundenTabelleView()
    {
        setDefaultRenderer(Object.class, new BigRenderer());
        setFillsViewportHeight(true);
        setRowHeight(56);
        getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 18));
    }
}
