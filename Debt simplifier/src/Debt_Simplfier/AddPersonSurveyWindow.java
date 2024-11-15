package Debt_Simplfier;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddPersonSurveyWindow extends JDialog{
	//Task Finished.
	public void initialize() {
		setSize(400,200);
		setLocationRelativeTo(null);
		setTitle("Add Person");
		setModal(true);
		setLayout(null);
		
		//components 
		JLabel textname = new JLabel("Name: ");
		textname.setBounds(50, 20, 100, 30);
		JTextField name = new JTextField();
		name.setFont(new Font("Arial", Font.PLAIN, 16));
		name.setBounds(120, 20, 230, 30);
		JLabel textid = new JLabel("ID: ");
		textid.setBounds(50, 60, 100, 30);
		JTextField id = new JTextField();
		id.setFont(new Font("Arial", Font.PLAIN, 16));
		id.setBounds(120, 60, 230, 30);
		JButton cancel = new JButton("Cancel");
		//cancel code
		cancel.addActionListener(e -> {
			dispose();
		});
		
		JButton submit = new JButton("Submit");
		//submit code
		JLabel idwarning = new JLabel();
		idwarning.setForeground(Color.red);
		idwarning.setBounds(120, 90, 230, 16);
		submit.addActionListener(e -> {
			String para1 = name.getText();
			String para2 = id.getText();
			if(para1.equals("") || para2.equals("")) {
				//System.out.println(para1 + "\t" + para2);
				JOptionPane.showMessageDialog(null, "Textfield is empty.", "Information", JOptionPane.INFORMATION_MESSAGE);
			}else if(GV.IDs.contains(para2)){
				JOptionPane.showMessageDialog(null, "This ID has already been taken.", "Information", JOptionPane.INFORMATION_MESSAGE);
				idwarning.setText("Choose a different ID");
				validate();
			}else{
				GV.people.add(new Person(para1, para2));
				GV.IDs.add(para2);
				dispose();
			}
		});
		
		cancel.setBounds(130, 110, 100, 30);
		submit.setBounds(250, 110, 100, 30);
		//add
		add(textname);
		add(name);
		add(textid);
		add(id);
		add(cancel);
		add(submit);
		add(idwarning);
		
		setResizable(false);
		setVisible(true);
	}
}
