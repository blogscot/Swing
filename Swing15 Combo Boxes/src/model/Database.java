package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Database {

	private List<Person> people;
	private Connection con;

	public Database() {
		people = new ArrayList<Person>();
	}

	public void connect() throws Exception {

		if (con == null) {

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new Exception("JDBC Driver not found");
			}

			String connectionUrl = "jdbc:mysql://localhost:3306/swingtest";
			con = DriverManager.getConnection(connectionUrl, "swinguser",
					"5ZCeDxLZQmdJ9xHL");

		}
	}

	public void disconnect() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("JDBC database: connection close failed.");
			}
		}
	}

	public void save() throws SQLException {

		String checkSql = "select count(*) as count from people where id=?";
		PreparedStatement checkStmt;
		int count = 0;
		
		checkStmt = con.prepareStatement(checkSql);

		for (Person person : people) {
			int id = person.getId();

			checkStmt.setInt(1, id);

			ResultSet result = checkStmt.executeQuery();
			
			result.next();
			
			count = result.getInt(1);
			
			System.out.println("Count for person " + id + " is "+ count);
		}

		checkStmt.close();

	}

	public void addPerson(Person person) {
		people.add(person);
	}

	public void removePerson(int index) {
		people.remove(index);
	}

	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}

	public void saveToFile(File file) throws IOException {

		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		Person[] persons = people.toArray(new Person[people.size()]);

		oos.writeObject(persons);

		oos.close();
	}

	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			Person[] persons = (Person[]) ois.readObject();

			people.clear();
			people.addAll(Arrays.asList(persons));

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ois.close();
	}
}
