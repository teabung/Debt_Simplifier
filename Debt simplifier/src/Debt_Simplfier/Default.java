package Debt_Simplfier;

//Default value of JComboBox for People[] argument
public class Default extends Person {
	public Default() {
		super.name = "<<Select>>";
	}
	@Override
	public String toString() {
		return name;
	}
}
