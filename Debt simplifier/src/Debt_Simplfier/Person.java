package Debt_Simplfier;

import java.util.HashMap;
import java.util.Set;


public class Person {
	//attributes
	public String name;
	private String id;
	protected HashMap<Person, Double> debts;//you need to pay
	protected HashMap<Person, Double> credits;//you need to receive
	
	//constructors
	public Person() {
		name = null;
		id = null;
		debts = new HashMap<>();
		credits = new HashMap<>();
	}
	public Person(String name, String id) {
		this.name = name;
		this.id = id;
		debts = new HashMap<>();
		credits = new HashMap<>();
	}
	
	//methods
	public void borrow(Person p, double value) {
		if(debts.containsKey(p)) {
			debts.put(p, debts.get(p)+value);
			p.credits.put(this, p.credits.get(this)+value);
		}else {
			debts.put(p, value);
			p.credits.put(this, value);
		}
	}
	public void pay(Person p, double value) {
		if(!debts.containsKey(p)) {
			System.out.println("No such person");
			return;
		}
		debts.put(p, debts.get(p)-value);
		p.credits.put(this, p.credits.get(this)-value);
		if(debts.get(p) == 0) {
			//no more debts, credit has been paid -> removing relationship.
			debts.remove(p);
			p.credits.remove(this);
		}
	}
	public void paymentAdjustment(Person initial, Person target, double amount) {
		//person initial should be one of this instance's creditors.
		
		if(target == this) {
			String step = "Step "+(GV.descriptions.size()+1) + ": " + this + " and " + initial + " owes $" + amount + " each other, so we can omit this.";
			GV.descriptions.add(step);
			//this pay each other.
			pay(initial, amount);
			initial.pay(target, amount);
		}else {
			String step = "Step " + (GV.descriptions.size()+1) + ": " + this + " pays " + target + " $" + amount + " instead of paying the amount to " + initial + " and " + initial + " having to pay it to " + target + ".";
			GV.descriptions.add(step);
			//take initial person's debt out of this instance
			debts.remove(initial);
			initial.credits.remove(this);
			
			//add taken out debt to target person.
			borrow(target, amount);
			
			//subtract the amount of credit initial person was about to receive from this instance from the amount of debt initial person was indebted to target person.
			initial.pay(target, amount);
		}
	}
	public void clearRelationship() {
		Set<Person> setd = debts.keySet();
		Set<Person> setc = credits.keySet();
		Person[] debtors = new Person[setd.size()];
		Person[] creditors = new Person[setc.size()];
		int i = 0;
		for(Person p : setd) debtors[i++] = p;
		i = 0;
		for(Person p : setc) creditors[i++] = p;
		for(Person d : debtors) pay(d, debts.get(d));
		for(Person c : creditors) c.pay(this, credits.get(c));
	}
	
	//getter methods
	public String getID() {return id;}
	public HashMap<Person, Double> getDebtInformation() {return debts;}
	public HashMap<Person, Double> getCreditInformation() {return credits;}
	
	//Method Override
	@Override
	public String toString() {
		return name+"("+id+")";
	}
	
	//test
	/*public static void main(String[] args) {
		Person taeyoung = new Person("Taeyoung", "1");
		Person yijia = new Person("Yijia", "2");
		Person trevor = new Person("Trevor", "3");
		Person simon = new Person("Simon", "4");
		
		taeyoung.borrow(trevor, 4);
		taeyoung.borrow(yijia, 9);
		simon.borrow(taeyoung, 4);
		
		System.out.println("Information: " + "\nTaeyoung: " + taeyoung.debts + "\t" + taeyoung.credits +
				"\nYijia: " + yijia.debts + "\t" + yijia.credits +
				"\nTrevor: " + trevor.debts + "\t" + trevor.credits +
				"\nSimon: " + simon.debts + "\t" + simon.credits);
		
		simon.paymentAdjustment(taeyoung, yijia);
		
		System.out.println("Information: " + "\nTaeyoung: " + taeyoung.debts + "\t" + taeyoung.credits +
				"\nYijia: " + yijia.debts + "\t" + yijia.credits +
				"\nTrevor: " + trevor.debts + "\t" + trevor.credits +
				"\nSimon: " + simon.debts + "\t" + simon.credits);
	}*/ 
}
