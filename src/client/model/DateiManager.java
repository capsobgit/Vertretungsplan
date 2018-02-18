package client.model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import shared.CellData;

/**
 * Hilfsklasse welche für das Model Daten in Datei ließt und schreibt. Die
 * IO-Operationen könnten in einer Methode vereinigt werden (Muss noch geändert
 * werden, sowie auch die Leseoperationen verlagert werden müssen)
 *
 * @author Bruno Sobral
 * @version 1.2
 */
public class DateiManager
{

    private final String LEHRER_LISTE = Bezeichnungen.Lehrerkuerzel.getName();
    private final String KLASSEN_LISTE = Bezeichnungen.Klassendatei.getName();
    private final String TABELLEN_DATEN = Bezeichnungen.Tabellendaten.getName();
    private final String STATISTIK = Bezeichnungen.Statistik.getName();
    private final String FAECHER_LISTE = Bezeichnungen.Faecherkuerzel.getName();
    private Datei datei;

    public DateiManager()
    {
        //Dateien werden erzeugt falls sie noch nicht existieren.
        if (!new File(LEHRER_LISTE).isFile())
        {
            datei = new TextDatei(LEHRER_LISTE);
            datei.write(Bezeichnungen.Kuerzel.getName() + "\n");
        }
        if (!new File(KLASSEN_LISTE).isFile())
        {
            datei = new TextDatei(KLASSEN_LISTE);
            datei.write(Bezeichnungen.Stunden.getName());
        }
        if (!new File(TABELLEN_DATEN).isFile())
        {
            datei = new XMLDatei(TABELLEN_DATEN);
        }
        if (!new File(STATISTIK).isFile())
        {
            datei = new TextDatei(STATISTIK);
        }
        if (!new File(FAECHER_LISTE).isFile())
        {
            datei = new TextDatei(FAECHER_LISTE);
            datei.write(Bezeichnungen.Faecher.getName() + "\n");
        }
    }

    /**
     * @param klasse
     */
    public void writeKlassenListe(String klasse)
    {
        datei = new TextDatei(KLASSEN_LISTE);
        datei.write(klasse);
    }

    /**
     *
     * @param kuerzel
     */
    public void writeLehrerListe(String kuerzel)
    {
        datei = new TextDatei(LEHRER_LISTE);
        datei.write(kuerzel);
    }

    /**
     * Schreibt in die Liste der Schulfacher-Kuerzel
     *
     * @param fach
     */
    public void writeFaecherListe(String fach)
    {
        datei = new TextDatei(FAECHER_LISTE);
        datei.write(fach);
    }

    /**
     * Tabellenmodel wird in Datei gesichert
     *
     * @param obj
     */
    public void writeTabelle(CellData[][] obj)
    {

        try (XMLEncoder enc = new XMLEncoder(new FileOutputStream(TABELLEN_DATEN)))
        {
            enc.writeObject(obj);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Liest aus Liste der Lehrer-Kuerzel
     *
     * @return list
     */
    public ArrayList<String> readLehrer()
    {
        ArrayList<String> list = new ArrayList<>(50);
        String tmp = "";
        try (BufferedReader buff = new BufferedReader(
                new FileReader(LEHRER_LISTE)))
        {
            while ((tmp = buff.readLine()) != null)
            {
                list.add(tmp);
            }
        } catch (IOException io)
        {
            io.printStackTrace();
        }
        sort(list);
        return list;
    }

    /**
     * Liest aus Liste der Lehrer-Kuerzel
     *
     * @return list
     */
    public ArrayList<String> readFach()
    {
        ArrayList<String> list = new ArrayList<>(32);
        String tmp = "";
        try (BufferedReader buff = new BufferedReader(
                new FileReader(FAECHER_LISTE)))
        {
            while ((tmp = buff.readLine()) != null)
            {
                list.add(tmp);
            }
        } catch (IOException io)
        {
            io.printStackTrace();
        }
        sort(list);
        return list;
    }

    /**
     * Loescht einzelnes Element aus einer Liste
     *
     * @param pattern
     * @param file
     */
    public void deleteElementFrom(String pattern, String file)
    {
        //Datei mit Zeitstempel
        Instant tmpTime = Instant.now();
        File tmpFile = new File(tmpTime.toString());
        String line;
        try (BufferedReader inbuff = new BufferedReader(
                new FileReader(file)); BufferedWriter outbuff
                = new BufferedWriter(new FileWriter(tmpFile)))
        {
            while ((line = inbuff.readLine()) != null)
            {
                //Falls Leerzeichen enthalten
                line = line.trim();
                //Falls das zu löschende Ende gefunden wurde
                if (line.equals(pattern))
                {
                    continue;
                }
                outbuff.write(line + "\n");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        //künstliche Datei umbenennen
        tmpFile.renameTo(new File(file));
    }

    /**
     * Liest Bezeichnung der Klassen z.B. 8a, 7c ..
     *
     * @return list
     */
    public ArrayList<String> readKlassen()
    {
        ArrayList<String> list = new ArrayList<>();
        String tmp = "";
        try (BufferedReader buff = new BufferedReader(
                new FileReader(KLASSEN_LISTE)))
        {
            while ((tmp = buff.readLine()) != null)
            {
                list.add(tmp);
            }
        } catch (IOException io)
        {
            io.printStackTrace();
        }
        sort(list);
        return list;
    }

    /**
     * Liest die gespeicherten Tabellendaten
     *
     * @return data
     */
    public CellData[][] readCellData()
    {
        CellData[][] data = null;

        try (XMLDecoder dec = new XMLDecoder(new FileInputStream(TABELLEN_DATEN)))
        {
            data = (CellData[][]) dec.readObject();
        } catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        return data;
    }

    /**
     * Hier sollen die Umsätze verarbeitet, formatiert, gespeichert und in Form
     * einer Statistik repräsentiert werden.
     */
    public void writeStatistik()
    {
        try (FileWriter file = new FileWriter(STATISTIK, true))
        {
            /*
             * Muss noch implementiert werden !!
             */
            file.write("\n");
        } catch (IOException io)
        {
            io.printStackTrace();
        }
    }

    /**
     * Hilfsklasse zum sortieren einer Liste
     *
     * @param list
     */
    private void sort(ArrayList<String> list)
    {
        Collections.sort(list.subList(1, list.size()), (String s1, String s2) ->
        {
            //nach Stringlänge verglichen
            if (s1.length() < s2.length())
            {
                return -1;
            } //Es werden Strings gleicher Länge verglichen!!
            else if (s1.length() == s2.length())
            {
                return s1.compareToIgnoreCase(s2);
            } else
            {
                return 0;
            }
        });
    }
}
