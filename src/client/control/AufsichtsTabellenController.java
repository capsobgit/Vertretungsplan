package client.control;

import java.rmi.RemoteException;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import server.ServerTableModel;

/**
 * Registriert auf Änderungen in der Aufsichtstabelle im Client
 *
 * @author Bruno Sobral
 */
public class AufsichtsTabellenController implements TableModelListener
{

    private ServerTableModel remote;
    private JTable table;
    private String[] header;

    public AufsichtsTabellenController(ServerTableModel remote, JTable table,
            String[] header)
    {
        this.remote = remote;
        this.table = table;
        this.header = header;
    }

    @Override
    public void tableChanged(TableModelEvent e)
    {
        int type = e.getType();
        TableModel model = null;
        if (type == TableModelEvent.UPDATE)
        {
            model = (TableModel) e.getSource();
        }

        int row = table.getRowCount();
        int col = table.getColumnCount();
        Object[][] data = new Object[row][col];
        DefaultTableModel remModel
                = new DefaultTableModel(data, this.header);
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                remModel.setValueAt(model.getValueAt(i, j), i, j);
            }
        }

        table.clearSelection();
        try
        {
            remote.getRemoteModel(remModel);
        } catch (RemoteException e1)
        {
            e1.printStackTrace();
        }

    }
}
