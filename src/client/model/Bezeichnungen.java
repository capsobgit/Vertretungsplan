package client.model;

/**
 * Diese Enum-Klasse hält alle Bezeichnungen der Kontroll-Elemente als auch der
 * Dateinamen
 * 
 * @author sobdaro
 */
public enum Bezeichnungen
{
    //Fensterueberschrift
    Vertretungsplan("Vertretungsplan"),
    //Bezeichnung für Kontrollelemente
    Abschicken("Abschicken"), Heute("Heute"), FuerHeute("Für Heute"), 
    FolgenderTag("Folgender Tag"), Statistik("Statistik"), Speichern("Speichern"),
    Uebertragen("übertragen!"),
    LehrerHinzufuegen("Lehrer hinzufügen - entfernen"), Tabelle("Tabelle,"),
    KlasseHinzufuegen("Klasse hinzufügen - entfernen"),
    FaecherHinzufuegen("Fächer hinzufügen - entfernen"),
    //Dateielemente und Bezeichnung
    Klassendatei("klassen"), Lehrerkuerzel("lehrerkrzl"),
    Faecherkuerzel("faecherkrzl"), Tabellendaten("tabellendaten"),
    //Erste Zeile in Datei
    Kuerzel("Kürzel"), Stunden("Std."), Faecher("Fächer")
    ;
    String name;

    Bezeichnungen(String n)
    {
        name = n;
    }
    
    public String getName()
    {
        return this.name;
    }
}
