package client.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import client.control.FaecherListeController;
import client.control.KlassenListeController;
import client.control.KuerzlController;
import client.model.Model;
import client.model.IModelListener;

/**
 *
 * @author sobdaro
 */
@SuppressWarnings("serial")
public class MenuSubFenster extends JDialog
        implements IModelListener
{

    private JComboBox<Object> krzlCombo;
    private JComboBox<Object> fachCombo;
    private JComboBox<Object> klasseCombo;

    /**
     *
     * @param kuerzelController
     * @param klassenController
     * @param faecherController
     */
    public MenuSubFenster(KuerzlController kuerzelController,
            KlassenListeController klassenController,
            FaecherListeController faecherController)
    {
        setTitle("Konfiguration");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1, 1));

        JPanel hauptPanel = new JPanel();
        hauptPanel.setBorder(BorderFactory.createEtchedBorder());
        hauptPanel.setLayout(new GridLayout(4, 0));

        JPanel interPanel1 = new JPanel();
        JPanel interPanel2 = new JPanel();
        JPanel interPanel3 = new JPanel();

        interPanel1.setLayout(new BoxLayout(interPanel1, BoxLayout.LINE_AXIS));
        interPanel1.setBorder(BorderFactory.createRaisedBevelBorder());
        interPanel2.setLayout(new BoxLayout(interPanel2, BoxLayout.LINE_AXIS));
        interPanel2.setBorder(BorderFactory.createRaisedBevelBorder());
        interPanel3.setLayout(new BoxLayout(interPanel3, BoxLayout.LINE_AXIS));
        interPanel3.setBorder(BorderFactory.createRaisedBevelBorder());
        //Durch die Blöcke ist es möglich gleiche Namen zu vergeben
        {
            JPanel hinzu = new JPanel();
            JPanel hinweg = new JPanel();

            JTextField hinkuerzel = new JTextField(5);
            hinkuerzel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            hinkuerzel.addActionListener(kuerzelController);
            krzlCombo = new JComboBox<>();
            krzlCombo.setBorder(BorderFactory.createRaisedBevelBorder());
            krzlCombo.addActionListener(kuerzelController);
            krzlCombo.setPrototypeDisplayValue("-Liste-");

            hinzu.add(new JLabel("Kürzel:"));
            hinzu.add(Box.createHorizontalStrut(5));
            hinzu.add(hinkuerzel);
            hinweg.add(krzlCombo);

            interPanel1.add(hinzu);
            interPanel1.add(new JSeparator(SwingConstants.VERTICAL));
            interPanel1.add(Box.createHorizontalStrut(5));
            interPanel1.add(hinweg);
        }

        {
            JPanel hinzu = new JPanel();
            JPanel hinweg = new JPanel();

            JTextField klasse = new JTextField(5);
            klasse.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            klasse.addActionListener(klassenController);
            klasseCombo = new JComboBox<>();
            klasseCombo.setBorder(BorderFactory.createRaisedBevelBorder());
            klasseCombo.addActionListener(klassenController);
            klasseCombo.setPrototypeDisplayValue("-Liste-");

            hinzu.add(new JLabel("Klasse:"));
            hinzu.add(Box.createHorizontalStrut(3));
            hinzu.add(klasse);
            hinweg.add(klasseCombo);

            interPanel2.add(hinzu);
            interPanel2.add(new JSeparator(SwingConstants.VERTICAL));
            interPanel2.add(Box.createHorizontalStrut(5));
            interPanel2.add(hinweg);
        }

        {
            JPanel hinzu = new JPanel();
            JPanel hinweg = new JPanel();

            JTextField hinkuerzel = new JTextField(5);
            hinkuerzel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            hinkuerzel.addActionListener(faecherController);
            fachCombo = new JComboBox<>();
            fachCombo.setBorder(BorderFactory.createRaisedBevelBorder());
            fachCombo.addActionListener(faecherController);
            fachCombo.setPrototypeDisplayValue("-Liste-");

            hinzu.add(new JLabel("Fach:"));
            hinzu.add(Box.createHorizontalStrut(17));
            hinzu.add(hinkuerzel);
            hinweg.add(fachCombo);

            interPanel3.add(hinzu);
            interPanel3.add(new JSeparator(SwingConstants.VERTICAL));
            interPanel3.add(Box.createHorizontalStrut(5));
            interPanel3.add(hinweg);

        }
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.setBackground(Color.green);
        JLabel topLabel = new JLabel("Hinzufügen    -    Entfernen");
        labelPanel.add(topLabel);
        hauptPanel.add(labelPanel);
        hauptPanel.add(interPanel1);
        hauptPanel.add(interPanel2);
        hauptPanel.add(interPanel3);

        add(hauptPanel);
        setLocation(500, 400);
        setSize(getPreferredSize());
        setVisible(true);
    }

    /**
     *
     * @param model
     */
    @Override
    public void modelChanged(Model model)
    {
        //Die Kürzel-Combo ist als Beobachter angemeldet,
        //und wird hier aktualisiert
        if (krzlCombo != null)
        {
            krzlCombo.setModel(new DefaultComboBoxModel<>(
                    model.getLehrerKrzl().toArray()));
        }
        if (fachCombo != null)
        {
            fachCombo.setModel(new DefaultComboBoxModel<>(
                    model.getFaecher().toArray()));
        }
        if (klasseCombo != null)
        {
            klasseCombo.setModel(new DefaultComboBoxModel<>(
                    model.getKlassenHeader().toArray()));
        }
    }
}
