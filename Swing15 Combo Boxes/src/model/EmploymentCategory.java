package model;

public enum EmploymentCategory {
	employed("Employed"),
	selfEmployed("Self-Employed"),
	unemployed("Unemployed"),
	other("other");
	
	private String text;
	
	private EmploymentCategory(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
