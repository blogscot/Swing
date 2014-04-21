package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Person;

public class PersonTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<Person> db;

	private String[] columnNames = { "ID", "Name", "Occupation", "Age",
			"Employment", "US Citizen", "Tax ID", "Gender" };

	@Override
	public String getColumnName(int column) {

		return columnNames[column];
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		switch (column) {
		case 1:
		case 5:
			return true;
		default:
			return false;
		}
	}

	
	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		
		if (db == null) return;

		Person person = db.get(row);
		
		switch (column) {
		case 1:
			person.setName((String)aValue);
			break;
		case 5:
			person.setUsCitizen((Boolean)aValue);
		default:
		}
		
	}

	@Override
	public Class<?> getColumnClass(int column) {

		switch (column) {
		case 0:
			return Integer.class;
		case 5:
			return Boolean.class;
		case 1:
		case 2:
		case 3:
		case 4:
		case 6:
		case 7:
			return String.class;
		default:
			return null;		
		}
	}

	public void setData(List<Person> db) {
		this.db = db;
	}

	@Override
	public int getColumnCount() {

		return 8; // Columns 0..7, see switch below
	}

	@Override
	public int getRowCount() {

		return db.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Person person = db.get(row);

		switch (column) {
		case 0:
			return person.getId();
		case 1:
			return person.getName();
		case 2:
			return person.getOccupation();
		case 3:
			return person.getAgeCategory();
		case 4:
			return person.getEmploymentCategory();
		case 5:
			return person.isUsCitizen();
		case 6:
			return person.getTaxId();
		case 7:
			return person.getGender();
		}
		return null;
	}
}
