package client.view;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.control.HauptController;
import client.model.Model;
import client.model.Point;
import server.ServerTableModel;

@SuppressWarnings("serial")
public class TabellenSubFenster extends JDialog
{
	private final String[] componentsName = {"krank", "fach", "vertretung"};

	private JComboBox<Object> cb_krank, cb_vertretung, cb_fach;
	
	public TabellenSubFenster(final Model model, StundenTabelleView parent, 
			Point tablePoint, Point mousePoint, ServerTableModel remote)
	{
		super(null, "Kürzel-Liste", Dialog.ModalityType.DOCUMENT_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(3, 1));
		//Dieses Subfenster bei Schließung abmelden
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				//Bei Fensterschließung Aktion
			}
		});
		
		JPanel northpanel = initGui();
		JPanel southpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		southpanel.setBorder(BorderFactory.createRaisedBevelBorder());
//		JPanel southSouthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel middlepanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
		//middlepanel.setLayout(new BoxLayout(middlepanel, BoxLayout.X_AXIS));
		middlepanel.setBorder(BorderFactory.createTitledBorder("Manuelle Eingabe"));
		setSize(500, 200);
		setLocation(mousePoint.getEventRow(), mousePoint.getEventCol() + 110);
		
		cb_krank = new JComboBox<>(new DefaultComboBoxModel<>(
				model.getLehrerKrzl().toArray()));
		cb_krank.setPrototypeDisplayValue("-Liste-");
		cb_krank.setName("krank");
		
		cb_vertretung = new JComboBox<>(new DefaultComboBoxModel<>(
				model.getLehrerKrzl().toArray()));
		cb_vertretung.setPrototypeDisplayValue("-Liste-");
		cb_vertretung.setName("vertretung");
		
		cb_fach = new JComboBox<Object>(new DefaultComboBoxModel<>(
				model.getFaecher().toArray()));
		cb_vertretung.setPrototypeDisplayValue("-Liste-");
		cb_fach.setName("fach");
		
		JButton sendButton = new JButton("übertragen!");
//		JButton minusSend = new JButton("Minus übertragen!");
		JCheckBox plusBox = new JCheckBox("Plus");
//		JCheckBox minusBox = new JCheckBox("Minus");
		plusBox.setBorder(BorderFactory.createLineBorder(Color.cyan));
		HauptController handler = new HauptController(model, tablePoint,
				parent, componentsName, remote);
		JTextField freeInput = new JTextField(15);
		freeInput.setBorder(BorderFactory.createLoweredBevelBorder());
		freeInput.addActionListener(handler);
		cb_krank.addActionListener(handler);
		cb_fach.addActionListener(handler);
		cb_vertretung.addActionListener(handler);
		sendButton.addActionListener(handler);
		plusBox.addActionListener(handler);
//		minusBox.addActionListener(handler);
		northpanel.add(new JLabel("Ausfall:"));
		northpanel.add(cb_krank);
		northpanel.add(new JLabel("Fach:"));
		northpanel.add(cb_fach);
		northpanel.add(new JLabel("Vertretung:"));
		northpanel.add(cb_vertretung);
		northpanel.add(new JLabel("Plus-Stunde:"));
		middlepanel.add(new JLabel("Mit Enter vormerken:"));
		northpanel.add(plusBox);
		middlepanel.add(freeInput);
		southpanel.add(sendButton);
//		southSouthPanel.add(minusSend);
		add(northpanel);
		add(middlepanel);
		add(southpanel);
//		add(southSouthPanel);
		setVisible(true);
	}
	
	public JPanel initGui()
	{
		JPanel panel = new JPanel(new GridLayout(2, 4, 15, 10));
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		panel.setBackground(Color.green);
		return panel;
	}
	public String[] getComponentsName()
	{
		return componentsName;
	}
}
