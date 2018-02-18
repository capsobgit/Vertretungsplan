package client.model;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Bruno Sobral
 * @date 17.02.2018
 * @version 1.0
 */
public class TextDatei extends Datei
{

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
