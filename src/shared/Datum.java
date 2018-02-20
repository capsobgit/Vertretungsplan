package shared;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Kapslung der Datumsformattierung
 *
 * @author sobdaro
 */
public class Datum
{

    private String datum;

    public Datum(int modus)
    {
        switch (modus)
        {
            case 1:
            {
                //Aktuelles Datum
                LocalDate rohdatum = LocalDate.now();
                //Format setzen
                DateTimeFormatter ausgabe
                        = DateTimeFormatter.ofPattern("EEEE, d. MMMM Y", Locale.GERMAN);
                //Datum formatiert
                datum = rohdatum.format(ausgabe);
                break;
            }
            //Für die Tabelle Morgen
            case 2:
            {
                LocalDate rohdatum = LocalDate.now().plusDays(1);
                DateTimeFormatter ausgabe
                        = DateTimeFormatter.ofPattern("EEEE, d. MMMM Y", Locale.GERMAN);
                datum = rohdatum.format(ausgabe);
                //Das Wochenende wird übersprungen
                if (datum.startsWith("Samstag"))
                {
                    rohdatum = LocalDate.now().plusDays(3);
                    datum = rohdatum.format(ausgabe);
                }
                break;
            }
            //Dummy für ZufallsDatum
            default:
            {
                LocalDate rohdatum
                        = LocalDate.now().minusDays((int) ((Math.random()) * 10));
                DateTimeFormatter ausgabe
                        = DateTimeFormatter.ofPattern("EEEE, d. MMMM Y", Locale.GERMAN);
                datum = rohdatum.format(ausgabe);
                break;
            }
        }
    }

    public String getDatum()
    {
        return datum;
    }

    public void setDatum(String datum)
    {
        this.datum = datum;
    }
}
