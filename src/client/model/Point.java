package client.model;

/**
 * Simple Kapselung der Tabellenkoordinaten
 *
 * @author sobdaro
 */
public class Point
{

    private int eventTableRow;
    private int eventTableCol;

    /**
     *
     * @param eventRow
     * @param eventCol
     */
    public Point(int eventRow, int eventCol)
    {
        this.eventTableRow = eventRow;
        this.eventTableCol = eventCol;
    }

    /**
     *
     * @return eventTableRow
     */
    public int getEventTableRow()
    {
        return eventTableRow;
    }

    /**
     *
     * @return eventTableCol
     */
    public int getEventTableCol()
    {
        return eventTableCol;
    }
}
