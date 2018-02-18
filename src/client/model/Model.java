package client.model;

import java.util.ArrayList;
import javax.swing.table.TableModel;
import shared.CellData;

/**
 * Modellklasse mit Beobachtern
 *
 * @author Bruno Sobral
 */
public class Model
{

    private CellData[][] dataMatrixHeute;
    private CellData[][] dataMatrixMorgen;
    private ArrayList<String> klassenHeader;
    private ArrayList<String> lehrerKrzl;
    private ArrayList<String> faecher;
    private ArrayList<ModelListener> modelListeners;
    private DateiManager manager;
    private static final int STUNDEN_TAG = 9;

    public Model()
    {
        manager = new DateiManager();
        modelListeners = new ArrayList<>();
        klassenHeader = manager.readKlassen();
        lehrerKrzl = manager.readLehrer();
        faecher = manager.readFach();
        initializeDataMatrix();
        fireModelChanged();
    }

    /**
     * View-Komponenten angemelden
     *
     * @param listener
     */
    public void addModelListener(ModelListener listener)
    {
        this.modelListeners.add(listener);
        fireModelChanged();
    }

    private void initializeDataMatrix()
    {
        dataMatrixHeute = new CellData[STUNDEN_TAG][klassenHeader.size()];
        dataMatrixMorgen = new CellData[STUNDEN_TAG][klassenHeader.size()];
        for (int i = 0; i < dataMatrixHeute.length; i++)
        {
            for (int j = 0; j < dataMatrixHeute[i].length; j++)
            {
                dataMatrixHeute[i][j] = new CellData(false, "", "", "", false);
                dataMatrixMorgen[i][j] = new CellData(false, "", "", "", false);
            }
        }
        fireModelChanged();
    }

    public void removeModelListener(ModelListener listener)
    {
        modelListeners.remove(listener);
    }

    public CellData[][] getDataMatrix(int arg)
    {
        // Das ist Gift!!
        if (arg == 1)
        {
            return dataMatrixHeute;
        } else
        {
            return dataMatrixMorgen;
        }
    }

    // Hier wird ein Tabellenmodell in die Matrix aufgenommen
    public void setDataMatrix(TableModel m)
    {
        for (int i = 0; i < STUNDEN_TAG; i++)
        {
            for (int j = 0; j < klassenHeader.size(); j++)
            {
                dataMatrixHeute[i][j] = (CellData) m.getValueAt(i, j);
            }
        }
    }

    public CellData[][] getDataMatrix1()
    {
        return dataMatrixHeute;
    }

    public void setDataMatrix1(CellData[][] dataMatrix1)
    {
        this.dataMatrixHeute = dataMatrix1;
    }

    public CellData[][] getDataMatrix2()
    {
        return dataMatrixMorgen;
    }

    public void setDataMatrix2(CellData[][] dataMatrix2)
    {
        this.dataMatrixMorgen = dataMatrix2;
    }

    public ArrayList<ModelListener> getModelListeners()
    {
        return modelListeners;
    }

    public void setModelListeners(ArrayList<ModelListener> modelListeners)
    {
        this.modelListeners = modelListeners;
    }

    public DateiManager getManager()
    {
        return manager;
    }

    public void setManager(DateiManager manager)
    {
        this.manager = manager;
    }

    public static int getStundenTag()
    {
        return STUNDEN_TAG;
    }

    public void setFaecher(ArrayList<String> faecher)
    {
        this.faecher = faecher;
    }

    public void insertData(CellData data, int row, int col, int arg)
    {
        if (arg == 1)
        {
            this.dataMatrixHeute[row][col] = data;
        } else if (arg == 2)
        {
            this.dataMatrixMorgen[row][col] = data;
        }
        fireModelChanged();
    }

    public ArrayList<String> getFaecher()
    {
        return faecher;
    }

    public ArrayList<String> getKlassenHeader()
    {
        return klassenHeader;
    }

    public void setKlassenHeader(ArrayList<String> klassenHeader)
    {
        this.klassenHeader = klassenHeader;
        fireModelChanged();
    }

    public ArrayList<String> getLehrerKrzl()
    {
        return lehrerKrzl;
    }

    public void setLehrerKrzl(ArrayList<String> lehrerKrzl)
    {
        this.lehrerKrzl = lehrerKrzl;
    }

    public void writeToKrzlFile(String krzl)
    {
        manager.writeLehrerListe(krzl);
        // reresh der Kuerzelliste
        lehrerKrzl = manager.readLehrer();
        fireModelChanged();
    }

    public void writeToFaecherFile(String fach)
    {
        manager.writeFaecherListe(fach);
        // refresh der Faecherliste
        faecher = manager.readFach();
        fireModelChanged();
    }

    public void writeToKlassenFile(String klasse)
    {
        manager.writeKlassenListe(klasse);
        // update wie bei Lehrerkuerzl
        klassenHeader = manager.readKlassen();
        initializeDataMatrix();
        fireModelChanged();
    }

    public void removeFromKrzlFile(String item)
    {
        manager.deleteElementFrom(item,
                Bezeichnungen.Lehrerkuerzel.getName());
        lehrerKrzl = manager.readLehrer();
        fireModelChanged();
    }

    public void removeFromKlassenFile(String item)
    {
        manager.deleteElementFrom(item,
                Bezeichnungen.Klassendatei.getName());
        klassenHeader = manager.readKlassen();
        fireModelChanged();
    }

    public void removeFromFaecherFile(String item)
    {
        manager.deleteElementFrom(item,
                Bezeichnungen.Faecherkuerzel.getName());
        faecher = manager.readFach();
        fireModelChanged();
    }

    /**
     * Liest DefaultTableModel aus Datei
     * @param day 
     */
    public void initTabellenModel(int day)
    {
        if (day == 1)
        {
            dataMatrixHeute = (CellData[][]) manager.readCellData();
        } else
        {
            dataMatrixMorgen = (CellData[][]) manager.readCellData();
        }
        fireModelChanged();
    }

    /**
     * Speichert das Modell NUR von Heute
     */
    public void putTabellenModel()
    {
        manager.writeTabelle(dataMatrixHeute);
    }

    /**
     * Hier wird die Benachrichtigung der Beobachter ausgeführt
     */
    private void fireModelChanged()
    {
        for (ModelListener list : modelListeners)
        {
            list.modelChanged(this);
        }
    }
}
