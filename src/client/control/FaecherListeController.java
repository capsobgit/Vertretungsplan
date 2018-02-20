package client.control;

import client.model.Bezeichnungen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import client.model.Model;

/**
 * Registriert das Hinzufuegen und entnehmen von Faecherkuerzeln im Menu
 * Subfenster
 *
 * @author sobdaro
 */
public class FaecherListeController implements ActionListener
{

    private Model model;

    public FaecherListeController(Model model)
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
            if (text.length() < 6 && !text.equals(""))
            {
                model.writeToFaecherFile(text);
            } else
            {
                JOptionPane.showMessageDialog(null,
                        "Bitte Abkürzung verwenden");
            }
            field.setText("");
        }

        if (e.getSource() instanceof JComboBox)
        {
            JComboBox<?> cbox = (JComboBox<?>) e.getSource();
            String tmp = cbox.getSelectedItem().toString();
            if (tmp.equals(Bezeichnungen.Faecher.getName()) || tmp.equals(""))
            {
                return;
            } else
            {
                model.removeFromFaecherFile(tmp);
            }
        }
    }
}
