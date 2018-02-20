package shared;

import java.io.Serializable;

/**
 * Diese Klasse repräsentiert die Daten einer einzelnen Tabellenzelle
 *
 * @author sobdaro
 */
@SuppressWarnings("serial")
public class CellData implements Serializable
{
    
    private boolean plusStunde;
    private boolean isEmpty;
    private String fehlend, ersatz, fach;

    /**
     * Default Konstruktor für leere Zellen
     */
    public CellData()
    {

    }

    public CellData(boolean plusStunde, String failure,
            String subject, String substitute, boolean empty)
    {
        this.plusStunde = plusStunde;
        this.isEmpty = empty;
        this.fehlend = failure;
        this.ersatz = substitute;
        this.fach = subject;
    }

    public boolean isPlusStunde()
    {
        return plusStunde;
    }

    public void setPlusStunde(boolean plusStunde)
    {
        this.plusStunde = plusStunde;
    }

    public boolean isEmpty()
    {
        return isEmpty;
    }

    public void setEmpty(boolean isEmpty)
    {
        this.isEmpty = isEmpty;
    }

    public String getFehlend()
    {
        return fehlend;
    }

    public void setFehlend(String fehlend)
    {
        this.fehlend = fehlend;
    }

    public String getErsatz()
    {
        return ersatz;
    }

    public void setErsatz(String ersatz)
    {
        this.ersatz = ersatz;
    }

    public String getFach()
    {
        return fach;
    }

    public void setFach(String fach)
    {
        this.fach = fach;
    }
}
