package client.model;

/**
 * Dieser Typ wird Polymorph von verschiedenen Unterklassen verwendet.
 * 
 * @author sobdaro
 */
public abstract class Datei<T>
{

    protected String name;
    
    /**
     *
     * @param name
     */
    public Datei(String name)
    {
        this.name = name;
    }
    
    /**
     * @param param
     */
    public abstract void write(T param);
}
