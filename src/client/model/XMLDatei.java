package client.model;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import shared.CellData;

/**
 * @author sobdaro
 */
public class XMLDatei extends Datei<CellData[][]>
{

    /**
     *
     * @param name
     */
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
    public void write(CellData[][] param)
    {
        try (XMLEncoder enc = new XMLEncoder(new FileOutputStream(this.name)))
        {
            enc.writeObject(param);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
