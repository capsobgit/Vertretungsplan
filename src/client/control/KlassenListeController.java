package client.control;

import client.model.Bezeichnungen;
import java.awt.event.*;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Liest Text aus Benutzereingabe und lässt diesen in die Klassenliste-Datei
 * schreiben
 *
 * @author Bruno Sobral
 */
import client.model.Model;

public class KlassenListeController implements ActionListener
{

    private Model model;

    public KlassenListeController(Model model)
    {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() instanceof JTextField)
        {
            JTextField field = (JTextField) e.getSource();
            String text = field.getText();
            if (text.length() < 4 && !text.equals(""))
            {
                model.writeToKlassenFile(text);
            } else
            {
                JOptionPane.showMessageDialog(null,
                        "Bitte korrekte Bezeichnung eingeben:\n z.B. \"6a\"");
            }

            field.setText("");
        }
        if (e.getSource() instanceof JComboBox)
        {
            JComboBox<?> cbox = (JComboBox<?>) e.getSource();
            String tmp = cbox.getSelectedItem().toString();
            if (tmp.equals(Bezeichnungen.Stunden.getName()) || tmp.equals(""))
            {
                return;
            } else
            {
                model.removeFromKlassenFile(tmp);
            }
        }
    }
}
