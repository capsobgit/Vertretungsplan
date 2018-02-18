package server;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import shared.SmallRenderer;

/**
 * Eine View für die Aufsichtstabelle.
 *
 * @author Bruno Sobral
 */
@SuppressWarnings("serial")
public class ServerAufsichtsTabellenView extends JTable
{

    public ServerAufsichtsTabellenView()
    {
        setDefaultRenderer(Object.class, new SmallRenderer());
        setFillsViewportHeight(true);

        final String[] AufsichtsTabellenHeader =
        {
            "Superfrüh", "", "", "Altbau", "Neubau1", "Neubau2", "", ""
        };
        // Auf Wunsch vom Original-Papier übernommen
        Object[][] AufsichsTabelleStruktur =
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
                "7. Std. (MiPa) Mensa", "", "7. Std. (MiPa) Mensa", "", "", "", ""
            }
        };

        final DefaultTableModel model = new DefaultTableModel(
                AufsichsTabelleStruktur, AufsichtsTabellenHeader);

        //Tabellen Modell
        setModel(model);
        setFont(new Font("Sans Serif", Font.BOLD, 17));
        getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 18));
        setRowHeight(30);
    }
}
