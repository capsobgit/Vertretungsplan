package client.model;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author sobdaro
 */
public class TextDatei extends Datei<String>
{

    /**
     *
     * @param name
     */
    public TextDatei(String name)
    {
        super(name);
    }

    @Override
    public void write(String param)
    {
        try (FileWriter fw = new FileWriter(name, true))
        {
            fw.write(param + "\n");
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
