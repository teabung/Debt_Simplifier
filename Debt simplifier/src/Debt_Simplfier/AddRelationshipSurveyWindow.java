package Debt_Simplfier;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class AddRelationshipSurveyWindow extends JDialog {
	
	void initialize() {
		Person[] comboboxElements = new Person[GV.people.size()+1];
		comboboxElements[0] = GV.placeholder;
		int i = 1;
		for(Person p : GV.people) {
			comboboxElements[i] = p;
			i++;
		}
		
		setSize(400, 300);
		setLocationRelativeTo(null);
		setTitle("Add Relationship");
		setModal(true);
		setLayout(null);
		
		JPanel borrower = new JPanel();
		JPanel lender = new JPanel();
		
		//select
		borrower.setBounds(20, 20, 360, 40);
		borrower.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel txtborrower = new JLabel("Borrower: ");
		JComboBox<Person> objborrower = new JComboBox<Person>(comboboxElements);

		borrower.add(txtborrower);
		borrower.add(objborrower);
		
		//target
		lender.setBounds(20, 60, 360, 40);
		lender.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel txtlender = new JLabel("Lender: ");
		JComboBox<Person> objlender = new JComboBox<Person>(comboboxElements);
		
		lender.add(txtlender);
		lender.add(objlender);
		
		//warning message
		JLabel warning = new JLabel("");
		warning.setForeground(Color.red);
		warning.setBounds(20, 100, 360, 20);
		
		//radio button
		JRadioButton borrow = new JRadioButton("Borrow");
		JRadioButton compensate = new JRadioButton("Compensate");
		borrow.setBounds(20, 120, 140, 40);
		compensate.setBounds(20, 160, 140, 40);
		ButtonGroup bg = new ButtonGroup();
		bg.add(borrow);
		bg.add(compensate);
		
		borrow.setEnabled(false);
		compensate.setEnabled(false);
		
		//inputfield
		JPanel inputfield = new JPanel();
		inputfield.setBounds(180, 120, 160, 40);
		inputfield.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel txtamount = new JLabel("Amount($)");
		JTextField amount = new JTextField();
		amount.setPreferredSize(new Dimension(80, 25));
		amount.setEnabled(false);
		
		inputfield.add(txtamount);
		inputfield.add(amount);
		
		//cancel/submit field
		
		JPanel cancelSubmitField = new JPanel();
		cancelSubmitField.setBounds(180, 200, 160, 40);
		cancelSubmitField.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JButton cancel = new JButton("Cancel");
		JButton submit = new JButton("Submit");
		submit.setEnabled(false);
		
		cancelSubmitField.add(cancel);
		cancelSubmitField.add(submit);
		
		//radio button interactions
		borrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit.setEnabled(false);
				KeyListener[] keylisteners = amount.getKeyListeners();
				if(keylisteners.length != 0) amount.removeKeyListener(keylisteners[0]);
				amount.setText("0");
				amount.addKeyListener(new KeyListener() {
					public void keyTyped(KeyEvent e) {
						if(Character.isLetter(e.getKeyChar())) e.consume();
						else {
							try {
								String current = amount.getText()+e.getKeyChar();
								double value = Double.parseDouble(current);
								if(value == 0) submit.setEnabled(false);
								else submit.setEnabled(true);
							}catch(NumberFormatException error){e.consume();}
						}
					}
					public void keyPressed(KeyEvent e) {
						if(amount.getText().length()==1&&e.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
							amount.setText("0");
							e.consume();
						}else if(amount.getText().equals("0")&&Character.isDigit(e.getKeyChar())) amount.setText("");
					}
					public void keyReleased(KeyEvent e) {return;}
				});
				amount.setEnabled(true);
			}
		});
		compensate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submit.setEnabled(false);
				KeyListener[] keylisteners = amount.getKeyListeners();
				if(keylisteners.length != 0) amount.removeKeyListener(keylisteners[0]);
				
				Person d = (Person)objborrower.getSelectedItem();
				Person c = (Person)objlender.getSelectedItem();
				double maximum = (d.debts.containsKey(c)) ? d.debts.get(c) : -1;
				if(maximum == -1) amount.setEnabled(false);
				else {
					amount.setText("0");
					amount.setEnabled(true);
				}
				amount.addKeyListener(new KeyListener() {
					public void keyTyped(KeyEvent e) {
						if(Character.isLetter(e.getKeyChar())) e.consume();
						else {
							try {
								double value = Double.parseDouble(amount.getText()+e.getKeyChar());
								if(value > maximum) {
									amount.setText(Double.toString(maximum));
									e.consume();
								}
								if(value == 0) submit.setEnabled(false);
								else submit.setEnabled(true);
							}catch(NumberFormatException error){e.consume();}
						}
					}
					public void keyPressed(KeyEvent e) {
						if(amount.getText().length()==1&&e.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
							amount.setText("0");
							e.consume();
						}else if(amount.getText().equals("0")&&Character.isDigit(e.getKeyChar())) amount.setText("");
					}
					public void keyReleased(KeyEvent e) {return;}
				});
			}
		});
		
		//combobox element
		objborrower.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					Person d = (Person)objborrower.getSelectedItem();
					Person c = (Person)objlender.getSelectedItem();
					if(!d.equals(GV.placeholder)&&!c.equals(GV.placeholder)) {
						if(d.equals(c)) {
							warning.setText("Borrower and Lender cannot be the same person!");
							borrow.setEnabled(false);
							compensate.setEnabled(false);
							bg.clearSelection();
							amount.setEnabled(false);
							submit.setEnabled(false);
						}else {
							warning.setText("");
							borrow.setEnabled(true);
							compensate.setEnabled(true);
							bg.clearSelection();
							amount.setEnabled(false);
							submit.setEnabled(false);
						}
					}else{
						borrow.setEnabled(false);
						compensate.setEnabled(false);
						bg.clearSelection();
						amount.setEnabled(false);
						submit.setEnabled(false);
					}
				}
			}
		});
		objlender.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					Person d = (Person)objborrower.getSelectedItem();
					Person c = (Person)objlender.getSelectedItem();
					if(!d.equals(GV.placeholder)&&!c.equals(GV.placeholder)) {
						if(d.equals(c)) {
							warning.setText("Borrower and Lender cannot be the same person!");
							borrow.setEnabled(false);
							compensate.setEnabled(false);
							bg.clearSelection();
							amount.setEnabled(false);
							submit.setEnabled(false);
						}else {
							warning.setText("");
							borrow.setEnabled(true);
							compensate.setEnabled(true);
							bg.clearSelection();
							amount.setEnabled(false);
							submit.setEnabled(false);
						}
					}else{
						borrow.setEnabled(false);
						compensate.setEnabled(false);
						bg.clearSelection();
						amount.setEnabled(false);
						submit.setEnabled(false);
					}
				}
			}
		});
		
		//cancel/submit on-click interactions
		cancel.addActionListener(e -> {
			dispose();
		});
		
		submit.addActionListener(e -> {
			Person debtor = (Person)objborrower.getSelectedItem();
			Person crediter = (Person)objlender.getSelectedItem();
			if(borrow.isSelected()) {
				double amountborrowing = Double.parseDouble(amount.getText());
				debtor.borrow(crediter, amountborrowing);
			}else if(compensate.isSelected()){
				double amountpaying = Double.parseDouble(amount.getText());
				debtor.pay(crediter, amountpaying);
			}
			dispose();
		});
		
		//components add
		add(borrower);
		add(lender);
		add(warning);
		add(borrow);
		add(compensate);
		add(inputfield);
		add(cancelSubmitField);
		//components added
		setResizable(false);
		setVisible(true);
		
	}
}

