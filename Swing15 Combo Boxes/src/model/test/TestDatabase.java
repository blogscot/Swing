package model.test;

import java.sql.SQLException;

import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class TestDatabase {

	public static void main(String[] args) {
		System.out.println("Running database test");

		Database db = new Database();
		try {
			db.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db.addPerson(new Person("Janet", "Disabled", AgeCategory.adult, EmploymentCategory.employed, null, false, Gender.Female));
		db.addPerson(new Person("Steve", "Mechanic", AgeCategory.adult, EmploymentCategory.employed, null, false, Gender.Male));
		db.addPerson(new Person("Barbara", "Retired", AgeCategory.senior, EmploymentCategory.other, null, false, Gender.Female));
		
		try {
			db.save();
		} catch (SQLException e) {
			System.out.println("Saving to database failure.");
		}
		
		db.disconnect();
	}

}
