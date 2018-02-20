package client.model;

/**
 * Schnittstelle welche von den ViewKomponenten implementiert werden. Diese
 * bekommen dadurch eine Referenz auf das Model
 *
 * @author sobdaro
 */
public interface IModelListener
{

    public void modelChanged(Model model);
}
