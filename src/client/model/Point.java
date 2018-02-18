package client.model;

/**
 * Simple Kapselung der Tabellenkoordinaten
 *
 * @author sobdaro
 */
public class Point
{

    private int eventRow;
    private int eventCol;

    public Point(int eventRow, int eventCol)
    {
        this.eventRow = eventRow;
        this.eventCol = eventCol;
    }

    public int getEventRow()
    {
        return eventRow;
    }

    public int getEventCol()
    {
        return eventCol;
    }
}
