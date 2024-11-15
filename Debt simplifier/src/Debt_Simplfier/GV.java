package Debt_Simplfier;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;

//Global Variables that are accessible throughout the entire module
public class GV {
	public static JFrame mainwindow = new JFrame("Debt Simplifier");
	public static LinkedList<Person> people = new LinkedList<>();
	public static LinkedList<String> IDs = new LinkedList<>();
	public static Default placeholder = new Default();
	public static ArrayList<String> descriptions;
	public static int currentindex;
}
