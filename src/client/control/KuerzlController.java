package client.control;

import client.model.Bezeichnungen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import client.model.Model;

/**
 * @see KlassenlisteController
 * @author Bruno Sobral
 */
public class KuerzlController implements ActionListener
{

    private Model model;

    public KuerzlController(Model model)
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
            if (text.length() < 7 && !text.equals(""))
            {
                model.writeToKrzlFile(text);
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
            if (tmp.equals("") || tmp.equals(Bezeichnungen.Kuerzel.getName()))
            {
                return;
            } else
            {
                model.removeFromKrzlFile(tmp);
            }
        }
    }
}
