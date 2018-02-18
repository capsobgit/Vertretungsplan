package client.model;

/**
 * Schnittstelle welche von den ViewKomponenten implementiert werden. Diese
 * bekommen dadurch eine Referenz auf das Model
 *
 * @author Bruno Sobral
 */
public interface ModelListener
{

    public void modelChanged(Model model);
}
