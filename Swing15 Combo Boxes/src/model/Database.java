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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Database {

	private List<Person> people;
	private Connection con;

	private int port;
	private String user;
	private String password;

	public Database() {
		people = new ArrayList<Person>();
	}

	public void configure(int port, String user, String password)
			throws Exception {

		this.port = port;
		this.user = user;
		this.password = password;

		if (con != null) {
			disconnect();
			connect();
		}
	}

	public void connect() throws Exception {

		if (con != null)
			return;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("JDBC Driver not found");
		}

		String url = String.format("jdbc:mysql://localhost:%d/swingtest", port);

		con = DriverManager.getConnection(url, user, password);
		
	// 5ZCeDxLZQmdJ9xHL
	}

	public void disconnect() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("JDBC database: connection close failed.");
			}
		}
		
		con = null;
	}

	public void save() throws SQLException {

		String checkSql = "select count(*) as count from people where id=?";
		String insertSql = "insert into people (id, name, age, employment_status, occupation, us_citizen, tax_id, gender) values (?,?,?,?,?,?,?,?)";
		String updateSql = "update people set name=?, age=?, employment_status=?, occupation=?, us_citizen=?, tax_id=?, gender=? where id=?";

		PreparedStatement checkStmt;
		PreparedStatement insertStmt;
		PreparedStatement updateStmt;
		int count = 0;

		checkStmt = con.prepareStatement(checkSql);
		insertStmt = con.prepareStatement(insertSql);
		updateStmt = con.prepareStatement(updateSql);

		for (Person person : people) {

			int id = person.getId();
			String name = person.getName();
			EmploymentCategory emp = person.getEmploymentCategory();
			String occupation = person.getOccupation();
			AgeCategory age = person.getAgeCategory();
			boolean isUs = person.isUsCitizen();
			String tax = person.getTaxId();
			Gender gender = person.getGender();

			checkStmt.setInt(1, id);
			ResultSet result = checkStmt.executeQuery();
			result.next();

			count = result.getInt(1);

			int col = 1;

			// Person not found in database
			if (count == 0) {
				System.out.println("Inserting person " + id);

				insertStmt.setInt(col++, id);
				insertStmt.setString(col++, name);
				insertStmt.setString(col++, age.name());
				insertStmt.setString(col++, emp.name());
				insertStmt.setString(col++, occupation);
				insertStmt.setBoolean(col++, isUs);
				insertStmt.setString(col++, tax);
				insertStmt.setString(col, gender.name());

				insertStmt.executeUpdate();

			} else {
				System.out.println("Updating person " + id);

				updateStmt.setString(col++, name);
				updateStmt.setString(col++, age.name());
				updateStmt.setString(col++, emp.name());
				updateStmt.setString(col++, occupation);
				updateStmt.setBoolean(col++, isUs);
				updateStmt.setString(col++, tax);
				updateStmt.setString(col++, gender.name());
				updateStmt.setInt(col, id);

				updateStmt.executeUpdate();
			}
		}

		updateStmt.close();
		insertStmt.close();
		checkStmt.close();
	}

	public void load() throws SQLException {

		people.clear();

		String sql = "select id, name, age, employment_status, occupation, us_citizen, tax_id, gender from people order by id";
		Statement selectStmt = con.createStatement();

		ResultSet results = selectStmt.executeQuery(sql);

		while (results.next()) {

			int id = results.getInt("id");
			String name = results.getString("name");
			AgeCategory ageCategory = AgeCategory.valueOf(results
					.getString("age"));
			EmploymentCategory employmentCategory = EmploymentCategory
					.valueOf(results.getString("employment_status"));
			String occupation = results.getString("occupation");
			boolean usCitizen = results.getBoolean("us_citizen");
			String taxId = results.getString("tax_id");
			Gender gender = Gender.valueOf(results.getString("gender"));

			Person person = new Person(id, name, occupation, ageCategory,
					employmentCategory, taxId, usCitizen, gender);
			people.add(person);

			// System.out.println(person);
		}

		results.close();
		selectStmt.close();

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
