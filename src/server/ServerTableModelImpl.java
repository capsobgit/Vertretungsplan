package server;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
/**
 * 
 * @author Bruno Sobral
 */
public class ServerTableModelImpl extends UnicastRemoteObject
        implements ServerTableModel
{

    private static final long serialVersionUID = -6757255501131875906L;

    private JTable table;

    public ServerTableModelImpl(JTable table) throws RemoteException
    {
        this.table = table;
    }

    @Override
    public void getRemoteModel(final DefaultTableModel model)
            throws RemoteException
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                table.setModel(model);
                model.fireTableDataChanged();
            }
        });
    }
}
