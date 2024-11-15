package Debt_Simplfier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

import Debt_Simplfier.Person;

public class Simplify {
	
	static TreeMap<Integer, ArrayList<Person[]>> possiblepath = new TreeMap<>(Collections.reverseOrder());
	
	/*public static void main(String[] args) {
		LinkedList<Person> people = new LinkedList<>();
		Person p1 = new Person("P1", "1");
		Person p2 = new Person("P2", "2");
		Person p3 = new Person("P3", "3");
		Person p4 = new Person("P4", "4");
		/*Person p5 = new Person("P5", "5");
		Person p6 = new Person("P6", "6");
		Person p7 = new Person("P7", "7");
		
		p1.borrow(p2, 50);
		p2.borrow(p3, 60);
		p2.borrow(p4, 80);
		p3.borrow(p4, 40);
		p3.borrow(p1, 50);
		p4.borrow(p1, 50);
		
		people.add(p1);
		people.add(p2);
		people.add(p3);
		people.add(p4);
		
		System.out.println("p1: " + p1.getDebtInformation() + "and credit: " + p1.getCreditInformation() +  
				"\np2: " + p2.getDebtInformation() + "and credit: " + p2.getCreditInformation() +  
				"\np3: " + p3.getDebtInformation() + "and credit: " + p3.getCreditInformation() +  
				"\np4: " + p4.getDebtInformation() + "and credit: " + p4.getCreditInformation());
		
		simplify(people);
		
		System.out.println("\np1: " + p1.getDebtInformation() + "and credit: " + p1.getCreditInformation() +  
				"\np2: " + p2.getDebtInformation() + "and credit: " + p2.getCreditInformation() +  
				"\np3: " + p3.getDebtInformation() + "and credit: " + p3.getCreditInformation() +  
				"\np4: " + p4.getDebtInformation() + "and credit: " + p4.getCreditInformation());
	}*/
	public static void simplify(LinkedList<Person> people) {
		GV.descriptions = new ArrayList<>();
		do {
			for(Person p : people) {
				boolean restart = false;
				Set<Person> setd = p.getDebtInformation().keySet();
				Person[] debtors = new Person[setd.size()];
				int i = 0;
				for(Person d : setd) debtors[i++] = d;
				for(Person debtor : debtors) {
					ArrayList<Person[]> current = new ArrayList<>();
					ArrayList<Person> visited = new ArrayList<>();
					visited.add(p);
					possiblepath = new TreeMap<>(Collections.reverseOrder());
					double n = p.getDebtInformation().get(debtor);
					BFS(p, debtor, n, 0, current, visited);
					
					if(!possiblepath.isEmpty()) {
						System.out.println("for " + p + ": \n" + possiblepath);
						ArrayList<Person[]> changes = possiblepath.firstEntry().getValue();
						
						for(Person[] arrangement : changes) p.paymentAdjustment(arrangement[0], arrangement[1], n);
						restart = true;
						break;
					}
				}
				if(restart) break;
			}
		}while(!possiblepath.isEmpty());
	}
	//value n1, n2 given.
	//when there is only one n
	public static void BFS(Person initial, Person t, double n, int arrowremoval, ArrayList<Person[]> currentpath, ArrayList<Person> visited) {
		System.out.println(n + "\nCurrently: " + t + "\n" + currentpath + "\n" );
		Set<Person> target = t.getDebtInformation().keySet();
		boolean cycle = false;
		if(!visited.contains(t)) {
			for(Person p : target) {
				ArrayList<Person> visitedclone = (ArrayList<Person>) visited.clone();
				visitedclone.add(p);
				System.out.println("Initial: " + initial + "\ntarget: " + t + "\ntarget's debtor: " + p);
				cycle = false;
				double m = t.getDebtInformation().get(p);
				//p == initial
				if(p == initial) {
					cycle = true;
					if(n == m) {
						Person[] arrangepayment = {t, p};
						ArrayList<Person[]> clone = (ArrayList<Person[]>)currentpath.clone();
						clone.add(arrangepayment);
						possiblepath.put(arrowremoval+2, clone);
					}else if(n < m) {
						Person[] arrangepayment = {t, p};
						ArrayList<Person[]> clone = (ArrayList<Person[]>)currentpath.clone();
						clone.add(arrangepayment);
						possiblepath.put(arrowremoval+1, clone);
					}
				}else {
					//one n
					if(n == m) {
						Person[] arrangepayment = {t, p};
						ArrayList<Person[]> clone = (ArrayList<Person[]>)currentpath.clone();
						clone.add(arrangepayment);
						BFS(initial, p, n, arrowremoval+1, clone, visitedclone);
					}
					//two n
					else if(n < m) {
						Person[] arrangepayment = {t, p};
						ArrayList<Person[]> clone = (ArrayList<Person[]>)currentpath.clone();
						clone.add(arrangepayment);
						BFS(initial, p, n, arrowremoval, clone, visitedclone);
					}
				}
			}
		}else {
			if(possiblepath.containsKey(arrowremoval)) {
				if(currentpath.size() > possiblepath.get(arrowremoval).size()) possiblepath.put(arrowremoval, currentpath);
			}else if(!currentpath.isEmpty()&&!cycle) {
				possiblepath.put(arrowremoval, currentpath);
			}
		}
	}
	//when there are n1 and n2
	/*public static void bruteforce(Person p2, Person t, double n1, double n2, int arrowremoval, ArrayList<Person[]> currentpath) {
		Set<Person> target = t.getDebtInformation().keySet();
		for(Person p : target) {
			double m = t.getDebtInformation().get(p);
			
		}
	}*/
}