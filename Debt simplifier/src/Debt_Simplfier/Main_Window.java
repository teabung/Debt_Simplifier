package Debt_Simplfier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class Main_Window {
	
	public static void main(String[] args) throws IOException {
		JFrame mainwindow = GV.mainwindow;
		mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainwindow.setSize(840, 680);
		mainwindow.setLocationRelativeTo(null);
		mainwindow.setLayout(null);
		int W = mainwindow.getSize().width;
		int H = mainwindow.getSize().height;
		
		//listing out infos in display
		JPanel display = new JPanel();
		//display.setBounds(0, 0, (int)(W*(0.75)), (int)(H*(0.6)));
		display.setLayout(new BoxLayout(display, BoxLayout.Y_AXIS));
		display.setBackground(Color.white);
		updateGV();
		//System.out.println(GV.people);
		updateDisplay(display);
		JScrollPane jspDisplay = new JScrollPane(display);
		jspDisplay.setBounds(0, 0, (int)(W*(0.75)), (int)(H*(0.6)));
		jspDisplay.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		//button window
		JPanel Buttonwindow = new JPanel();
		Buttonwindow.setLayout(new BoxLayout(Buttonwindow, BoxLayout.Y_AXIS));
		Buttonwindow.setBounds((int)(W*0.75), 0, (int)(W*0.25), (int)(H*0.6));
		Buttonwindow.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		int gap = (int)(Buttonwindow.getSize().height*0.3)/5;
		int bH = (int)(Buttonwindow.getSize().height*0.7)/4;
		int bW = (int)((W*0.25));
		//add all the buttons
		Buttonwindow.add(Box.createVerticalStrut(gap));
		JButton addperson = new JButton("Add Person");
		addperson.setMaximumSize(new Dimension(bW, bH));
		addperson.setMinimumSize(new Dimension(bW, bH));
		
		//code for addperson
		addperson.addActionListener(e -> {
			System.out.println(GV.people);
			System.out.println("Adding a person");
			AddPersonSurveyWindow form = new AddPersonSurveyWindow();
			form.initialize();
			updateDisplay(display);
			try {
				updateFiles();
			} catch (IOException e1) {}
			jspDisplay.setViewportView(display);
		});
		
		Buttonwindow.add(addperson);
		//----------------------------------------------------------//
		Buttonwindow.add(Box.createVerticalStrut(gap));
		JButton addrelationship = new JButton("Add Relationship");
		addrelationship.setMaximumSize(new Dimension(bW, bH));
		addrelationship.setMinimumSize(new Dimension(bW, bH));
		
		//code for addrelationship
		addrelationship.addActionListener(e -> {
			System.out.println("Adding a relationship");
			AddRelationshipSurveyWindow form = new AddRelationshipSurveyWindow();
			form.initialize();
			try {
				updateFiles();
			} catch (IOException e1) {}
		});
		
		Buttonwindow.add(addrelationship);
		//----------------------------------------------------------//
		Buttonwindow.add(Box.createVerticalStrut(gap));
		JButton simplify = new JButton("Simplify");
		simplify.setMaximumSize(new Dimension(bW, bH));
		simplify.setMinimumSize(new Dimension(bW, bH));
		
		//code for simplify
		simplify.addActionListener(e -> {
			System.out.println("Simplifying");
		});
		
		Buttonwindow.add(simplify);
		//----------------------------------------------------------//
		Buttonwindow.add(Box.createVerticalStrut(gap));
		JButton steps = new JButton("Simplification Detail");
		steps.setMaximumSize(new Dimension(bW, bH));
		steps.setMinimumSize(new Dimension(bW, bH));
		steps.setEnabled(false);
		
		
		Buttonwindow.add(steps);
		Buttonwindow.add(Box.createVerticalStrut(gap));
		
		//TODO: scrollpane still doesn't work here but it's ok
		//detailwindow
		JPanel Detailwindow = new JPanel();
		Detailwindow.setLayout(new BorderLayout());
		Detailwindow.setBackground(Color.white); 
		
		JScrollPane jspDW = new JScrollPane(Detailwindow);
		jspDW.setBounds(0, (int)(H*0.6), W, (int)(H*0.4)-35);
		jspDW.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		//code for steps
		steps.addActionListener(e ->{
			updateSteps(Detailwindow);
			System.out.println("listing out steps");
		});
		simplify.addActionListener(e -> {
			Simplify.simplify(GV.people);
			updateDisplay(display);
			try {
				updateFiles();
			} catch (IOException e1) {}
			if(!GV.descriptions.isEmpty()) steps.setEnabled(true);
			else {
				steps.setEnabled(false);
				Detailwindow.removeAll();
				Detailwindow.add(new JLabel("The map has already been simplified.", SwingConstants.CENTER), BorderLayout.CENTER);
				jspDW.setViewportView(Detailwindow);
			}
		});
		
		//mainwindow modification.
		mainwindow.add(jspDisplay);
		mainwindow.add(Buttonwindow);
		mainwindow.add(jspDW);
		/*mainwindow.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				
			}
		});*/
		//mainwindow.setLayout(new BorderLayout());
		mainwindow.setResizable(false);
		mainwindow.setVisible(true);
	}
	private static void updateFiles() throws IOException {
		FileWriter data_people = new FileWriter("data-people");
		FileWriter data_ids = new FileWriter("data-ids");
		FileWriter data_debts = new FileWriter("data-debts");
		for(Person p : GV.people) {
			data_people.write(p.name+"\n");
			data_ids.write(p.getID()+"\n");
			String debtinfo = "";
			Set<Person> debtors = p.getDebtInformation().keySet();
			for(Person d : debtors) {
				System.out.println("writing");
				debtinfo += GV.people.indexOf(d) + " " + p.getDebtInformation().get(d) + " ";
			}
			data_debts.write(debtinfo+"\n");
		}
		data_people.close();
		data_ids.close();
		data_debts.close();
	}
	private static void updateGV() throws IOException {
		BufferedReader data_people = new BufferedReader(new FileReader("data-people"));
		BufferedReader data_ids = new BufferedReader(new FileReader("data-ids"));
		BufferedReader data_debts = new BufferedReader(new FileReader("data-debts"));
		
		String datum = "";
		while((datum = data_people.readLine()) != null) {
			Person p = new Person(datum, data_ids.readLine());
			GV.people.add(p);
		}
		for(Person p : GV.people) {
			String[] packet_debts = data_debts.readLine().split(" ");
			//System.out.println(Arrays.toString(packet_debts) + "\n" + packet_debts.length);
			for(int i = 0; i < packet_debts.length-1; i+=2) p.borrow(GV.people.get(Integer.parseInt(packet_debts[i])), Double.parseDouble(packet_debts[i+1]));
		}
		data_people.close();
		data_ids.close();
		data_debts.close();
	}
	private static void updateDisplay(JPanel dis) {
		dis.removeAll();
		LinkedList<Person> people = GV.people;
		for(Person p : people) {
			JPanel information = new JPanel();
			information.setBackground(Color.white);//debugging purpose
			information.setLayout(new BoxLayout(information, BoxLayout.X_AXIS));
			//information.setPreferredSize(new Dimension(dis.getSize().width, 10)); <- problem! Took me 2 weeks to find this.
			information.setAlignmentX(Box.LEFT_ALIGNMENT);
			JButton X = new JButton("X");
			X.setBackground(Color.red);
			JButton individual = new JButton("Info");
			JLabel person = new JLabel("    ->    "+p.toString());
			//code for remove button
			X.addActionListener(e -> {
				p.clearRelationship();
				GV.people.remove(p);
				GV.IDs.remove(p.getID());
				updateDisplay(dis);
				try {
					updateFiles();
				} catch (IOException e1) {}
				dis.repaint();
			});
			
			//code for individual information
			individual.addActionListener(e -> {
				InfoWindow tab = new InfoWindow();
				tab.initialize(p);
			});
			information.add(X);
			information.add(individual);
			information.add(person);
			dis.add(information);
		}
		dis.validate();
	}
	private static void updateSteps(JPanel display) {
		GV.currentindex = 0;
		display.removeAll();
		Object[] procedures = GV.descriptions.toArray();
		JButton previous = new JButton("<<");
		previous.setEnabled(false);
		JButton next = new JButton(">>");
		JLabel remainingstep = new JLabel(1+"/"+procedures.length);
		if(procedures.length==1) next.setEnabled(false);
		JLabel currentstep = new JLabel((String)procedures[0], SwingConstants.CENTER);
		
		previous.addActionListener(e -> {
			next.setEnabled(true);
			if(GV.currentindex -1 == 0) {
				GV.currentindex--;
				previous.setEnabled(false);
				currentstep.setText((String)procedures[GV.currentindex]);
				remainingstep.setText((GV.currentindex+1)+"/"+procedures.length);
			}else {
				GV.currentindex--;
				currentstep.setText((String)procedures[GV.currentindex]);
				remainingstep.setText((GV.currentindex+1)+"/"+procedures.length);
			}
		});
		next.addActionListener(e -> {
			previous.setEnabled(true);
			if(GV.currentindex+1 == procedures.length-1) {
				GV.currentindex++;
				next.setEnabled(false);
				currentstep.setText((String)procedures[GV.currentindex]);
				remainingstep.setText((GV.currentindex+1)+"/"+procedures.length);
			}else {
				GV.currentindex++;
				currentstep.setText((String)procedures[GV.currentindex]);
				remainingstep.setText((GV.currentindex+1)+"/"+procedures.length);
			}
		});
		
		JPanel Buttonpanel = new JPanel(new FlowLayout());
		Buttonpanel.add(previous);
		Buttonpanel.add(remainingstep);
		Buttonpanel.add(next);
		
		//add all
		display.add(currentstep, BorderLayout.CENTER);
		display.add(Buttonpanel, BorderLayout.SOUTH);
		display.validate();
	}
}
