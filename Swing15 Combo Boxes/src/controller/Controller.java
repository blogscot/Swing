package controller;

import gui.FormEvent;
import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class Controller {

	Database db = new Database();

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
			genderCategory = Gender.male;
			break;
		case "Female":
			genderCategory = Gender.female;
			break;
		default:
			// Prevent a null result
			genderCategory = Gender.female;
		}

		Person person = new Person(name, occupation, ageCategory,
				employmentCategory, taxId, usCitizen, genderCategory);
		
		db.addPerson(person);

	}
}