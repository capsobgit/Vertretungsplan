package server;

import java.rmi.*;
import javax.swing.table.DefaultTableModel;

/**
 * Entfernte Methode, mit der der Server auf die 
 * 
 * @author sobdaro
 */

public interface IServerTableModel extends Remote
{
	public void getRemoteModel(DefaultTableModel model)
			throws RemoteException;
}
