package controller;

import gui.FormEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class Controller {

	Database db = new Database();

	public List<Person> getPeople() {
		return db.getPeople();
	}

	public void save() throws SQLException {
		db.save();
	}

	public void connect() throws Exception {
		db.connect();
	}

	public void load() throws SQLException {
		db.load();
	}

	public void disconnect() {
		db.disconnect();
	}

	public void addPerson(FormEvent ev) {

		String name = ev.getName();
		String occupation = ev.getOccupation();
		int ageCatId = ev.getAgeCategory();
		String empCat = ev.getEmploymentCategory();
		String taxId = ev.getTaxId();
		boolean usCitizen = ev.IsUsCitizen();
		String gender = ev.getGender();

		AgeCategory ageCategory;

		switch (ageCatId) {
		case 0:
			ageCategory = AgeCategory.child;
			break;
		case 1:
			ageCategory = AgeCategory.adult;
			break;
		case 2:
			ageCategory = AgeCategory.senior;
			break;
		default:
			// Prevent a null result
			ageCategory = AgeCategory.child;
		}

		EmploymentCategory employmentCategory;

		switch (empCat) {
		case "Employed":
			employmentCategory = EmploymentCategory.employed;
			break;
		case "Self-Employed":
			employmentCategory = EmploymentCategory.selfEmployed;
			break;
		case "Unemployed":
			employmentCategory = EmploymentCategory.unemployed;
		default:
			employmentCategory = EmploymentCategory.other;
		}

		Gender genderCategory;

		switch (gender) {
		case "Male":
			genderCategory = Gender.Male;
			break;
		case "Female":
			genderCategory = Gender.Female;
			break;
		default:
			// Prevent a null result
			genderCategory = Gender.Female;
		}

		Person person = new Person(name, occupation, ageCategory,
				employmentCategory, taxId, usCitizen, genderCategory);

		db.addPerson(person);

	}

	public void removePerson(int index) {
		db.removePerson(index);
	}

	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
	}

	public void loadFromFile(File file) throws IOException {
		db.loadFromFile(file);
	}
}