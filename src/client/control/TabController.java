package client.control;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Diese Klasse regelt die Sichtbarkeit der Buttons inerhalb des TabbedPanes
 *
 * @author sobdaro
 */
public class TabController implements ChangeListener
{

    private JButton b1, b2;
    private JLabel l1, l2;

    /**
     *
     * @param b1
     * @param b2
     * @param l1
     * @param l2
     */
    public TabController(JButton b1, JButton b2, JLabel l1, JLabel l2)
    {
        this.b1 = b1;
        this.b2 = b2;
        this.l1 = l1;
        this.l2 = l2;
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        JTabbedPane pane = (JTabbedPane) e.getSource();
        int index = pane.getSelectedIndex();
        switch (index)
        {
            case 0:
                b1.setVisible(true);
                b2.setVisible(false);
                l1.setVisible(true);
                l2.setVisible(false);
                break;
            case 1:
                b2.setVisible(true);
                b1.setVisible(false);
                l2.setVisible(true);
                l1.setVisible(false);
                break;
            default:
                b1.setVisible(false);
                b2.setVisible(false);
                l1.setVisible(true);
                l2.setVisible(false);
                break;
        }
    }
}