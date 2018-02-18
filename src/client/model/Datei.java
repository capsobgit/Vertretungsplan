package client.model;

/**
 * Dieser Typ wird Polymorph von verschiedenen Unterklassen verwendet.
 * 
 * @author Bruno Sobral
 * @date 17.02.2018
 * @version 1.0
 */
public abstract class Datei 
{
    protected String name;
    
    public Datei(String name)
    {
        this.name = name;
    }
    
    /**
     * @param param
     */
    public abstract void write(String param);
}
