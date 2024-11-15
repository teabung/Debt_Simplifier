package Debt_Simplfier;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class InfoWindow extends JDialog{
	
	public void initialize(Person p) {
		setSize(480,320);
		setLocationRelativeTo(null);
		setTitle("Information");
		setModal(true);
		setLayout(null);
		
		//top: name
		JPanel intro = new JPanel();
		intro.setBounds(0, 0, 480, 80);
		intro.setLayout(new BoxLayout(intro, BoxLayout.Y_AXIS));
		intro.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		JLabel name = new JLabel(p.name);
		name.setFont(new Font("Ariel", Font.PLAIN, 40));
		name.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		JLabel id = new JLabel(p.getID());
		id.setFont(new Font("Ariel", Font.BOLD, 18));
		id.setForeground(Color.red);
		id.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		intro.add(name);
		intro.add(id);
		
		//bottom left: creditors list
		JPanel creditorsDisplay = new JPanel();
		//creditorsDisplay.setBounds(0, 80, 240, 240);
		//creditorsDisplay.setSize(240, 240);
		creditorsDisplay.setLayout(new BoxLayout(creditorsDisplay, BoxLayout.Y_AXIS));
		creditorsDisplay.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		HashMap<Person, Double> youneedtopay = p.getDebtInformation();
		creditorsDisplay.add(new JLabel("Borrowed From:"));
		for(Person per : youneedtopay.keySet()) {
			JLabel debtinfo = new JLabel();
			String text = per.toString() + " -> $" + youneedtopay.get(per);
			debtinfo.setText(text);
			creditorsDisplay.add(debtinfo);
		}
		JScrollPane jspforCreditors = new JScrollPane(creditorsDisplay);
		jspforCreditors.setBounds(0, 80, 240, 240);
		
		//bottom right: people in debt list
		JPanel debtorsDisplay = new JPanel();
		//debtorsDisplay.setBounds(240, 80, 240, 240);
		debtorsDisplay.setLayout(new BoxLayout(debtorsDisplay, BoxLayout.Y_AXIS));
		creditorsDisplay.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		HashMap<Person, Double> theyneedtopay = p.getCreditInformation();
		debtorsDisplay.add(new JLabel("Lended to:"));
		for(Person per : theyneedtopay.keySet()) {
			JLabel creditinfo = new JLabel();
			String text = per.toString() + " -> $" + theyneedtopay.get(per);
			creditinfo.setText(text);
			debtorsDisplay.add(creditinfo);
		}
		JScrollPane jspforDebtors = new JScrollPane(debtorsDisplay);
		jspforDebtors.setBounds(240, 80, 240, 240);
		
		//adding components
		add(intro);
		add(jspforCreditors);
		add(jspforDebtors);
		
		setResizable(false);
		setVisible(true);
	}
}
