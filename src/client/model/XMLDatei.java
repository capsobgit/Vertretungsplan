package client.model;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;

/**
 * @author Bruno Sobral
 * @date 17.02.2018
 * @version 1.0
 */
public class XMLDatei extends Datei
{

    public XMLDatei(String name)
    {
        super(name);
    }

    /**
     * Schreibt XML in Datei
     *
     * @param param
     */
    @Override
    public void write(String param)
    {
        try (XMLEncoder enc = new XMLEncoder(new FileOutputStream(param)))
        {
            enc.writeObject(param);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
