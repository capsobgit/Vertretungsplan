package client.model;

import java.util.ArrayList;
import javax.swing.table.TableModel;
import shared.CellData;

/**
 * Modellklasse mit Beobachtern
 *
 * @author sobdaro
 */
public class Model
{

    private CellData[][] dataMatrixHeute;
    private CellData[][] dataMatrixMorgen;
    private ArrayList<String> klassenHeader;
    private ArrayList<String> lehrerKrzl;
    private ArrayList<String> faecher;
    private ArrayList<IModelListener> modelListeners;
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
    public void addModelListener(IModelListener listener)
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

    /**
     *
     * @param listener
     */
    public void removeModelListener(IModelListener listener)
    {
        modelListeners.remove(listener);
    }

    /**
     *
     * @param arg
     * @return {datamatrixHeute|datamatrixMorgen}
     */
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

    /**
     * Hier wird ein Tabellenmodell in die Matrix aufgenommen
     * @param m
     */
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

    /**
     *
     * @return dataMatrixHeute
     */
    public CellData[][] getDataMatrix1()
    {
        return dataMatrixHeute;
    }

    /**
     *
     * @param dataMatrix1
     */
    public void setDataMatrix1(CellData[][] dataMatrix1)
    {
        this.dataMatrixHeute = dataMatrix1;
    }

    /**
     *
     * @return dataMatrixMorgen
     */
    public CellData[][] getDataMatrix2()
    {
        return dataMatrixMorgen;
    }

    /**
     *
     * @param dataMatrix2
     */
    public void setDataMatrix2(CellData[][] dataMatrix2)
    {
        this.dataMatrixMorgen = dataMatrix2;
    }

    /**
     *
     * @return modelListeners
     */
    public ArrayList<IModelListener> getModelListeners()
    {
        return modelListeners;
    }

    /**
     *
     * @param modelListeners
     */
    public void setModelListeners(ArrayList<IModelListener> modelListeners)
    {
        this.modelListeners = modelListeners;
    }

    /**
     *
     * @return manager
     */
    public DateiManager getManager()
    {
        return manager;
    }

    /**
     *
     * @param manager
     */
    public void setManager(DateiManager manager)
    {
        this.manager = manager;
    }

    /**
     *
     * @return STUNDEN_TAG
     */
    public static int getStundenTag()
    {
        return STUNDEN_TAG;
    }

    /**
     *
     * @param faecher
     */
    public void setFaecher(ArrayList<String> faecher)
    {
        this.faecher = faecher;
    }

    /**
     *
     * @param data
     * @param row
     * @param col
     * @param arg
     */
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

    /**
     *
     * @return faecher
     */
    public ArrayList<String> getFaecher()
    {
        return faecher;
    }

    /**
     *
     * @return klassenHeader
     */
    public ArrayList<String> getKlassenHeader()
    {
        return klassenHeader;
    }

    /**
     *
     * @param klassenHeader
     */
    public void setKlassenHeader(ArrayList<String> klassenHeader)
    {
        this.klassenHeader = klassenHeader;
        fireModelChanged();
    }

    /**
     *
     * @return lehrerKrzl
     */
    public ArrayList<String> getLehrerKrzl()
    {
        return lehrerKrzl;
    }

    /**
     *
     * @param lehrerKrzl
     */
    public void setLehrerKrzl(ArrayList<String> lehrerKrzl)
    {
        this.lehrerKrzl = lehrerKrzl;
    }

    /**
     *
     * @param krzl
     */
    public void writeToKrzlFile(String krzl)
    {
        manager.writeLehrerListe(krzl);
        // reresh der Kuerzelliste
        lehrerKrzl = manager.readLehrer();
        fireModelChanged();
    }

    /**
     *
     * @param fach
     */
    public void writeToFaecherFile(String fach)
    {
        manager.writeFaecherListe(fach);
        // refresh der Faecherliste
        faecher = manager.readFach();
        fireModelChanged();
    }

    /**
     *
     * @param klasse
     */
    public void writeToKlassenFile(String klasse)
    {
        manager.writeKlassenListe(klasse);
        // update wie bei Lehrerkuerzl
        klassenHeader = manager.readKlassen();
        initializeDataMatrix();
        fireModelChanged();
    }

    /**
     *
     * @param item
     */
    public void removeFromKrzlFile(String item)
    {
        manager.deleteElementFrom(item,
                Bezeichnungen.Lehrerkuerzel.getName());
        lehrerKrzl = manager.readLehrer();
        fireModelChanged();
    }

    /**
     *
     * @param item
     */
    public void removeFromKlassenFile(String item)
    {
        manager.deleteElementFrom(item,
                Bezeichnungen.Klassendatei.getName());
        klassenHeader = manager.readKlassen();
        fireModelChanged();
    }

    /**
     *
     * @param item
     */
    public void removeFromFaecherFile(String item)
    {
        manager.deleteElementFrom(item,
                Bezeichnungen.Faecherkuerzel.getName());
        faecher = manager.readFach();
        fireModelChanged();
    }

    /**
     * Liest DefaultTableModel aus Datei
     *
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
        for (IModelListener list : modelListeners)
        {
            list.modelChanged(this);
        }
    }
}
