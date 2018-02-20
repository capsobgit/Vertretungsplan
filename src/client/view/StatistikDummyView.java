package client.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import shared.Datum;
import client.model.Model;
import client.model.IModelListener;

@SuppressWarnings("serial")
public class StatistikDummyView extends JPanel implements IModelListener
{

    public StatistikDummyView()
    {
        setLayout(new GridLayout(0, 7));
        setBackground(Color.lightGray);
        add(new JLabel("Name"));
        add(new JLabel("1.Plusstunde"));
        add(new JLabel("2.Plusstunde"));
        add(new JLabel("3.Plusstunde"));
        add(new JLabel("4.Plusstunde"));
        add(new JLabel("5.Plusstunde"));
        add(new JLabel("6.Plusstunde"));
    }

    @Override
    public void modelChanged(Model model)
    {
        List<String> kuerzel = model.getLehrerKrzl().
                subList(2, model.getLehrerKrzl().size());
        for (String l : kuerzel)
        {
            add(new JLabel(l));
            add(new JLabel(new Datum(3).getDatum()));
            add(new JLabel(new Datum(3).getDatum()));
            add(new JLabel(new Datum(3).getDatum()));
            add(new JLabel((int) (Math.random() * 10) > 5
                    ? new Datum(3).getDatum() : ""));
            add(new JLabel());
            add(new JLabel());
        }
    }
}
