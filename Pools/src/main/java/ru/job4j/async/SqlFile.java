package ru.job4j.async;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CompletableFuture;

/**
 * 
 * @author kirill
 *
 */
public class SqlFile {
	private String[] headers;
	private int countryIndex;
	private int firstnameIndex;
	private int lastnameIndex;
	private boolean newCountry;
	private String thisCountry;
	private String lastCountry;
	private Connection conn;
	private final static String SQL_CREATE_COUNTRIES = 
			"CREATE TABLE COUNTRIES (id bigint auto_increment, country varchar(20))";
	private final static String SQL_CREATE_USERS = 
			"CREATE TABLE USERS (id bigint auto_increment, first_name varchar(20), last_name varchar(20))";
	private final static String SQL_INSERT_COUNTRIES = "INSERT INTO COUNTRIES(country) VALUES(?)";
	private final static String SQL_INSERT_USERS = "INSERT INTO USERS(first_name, last_name) VALUES(?, ?)";
	private final static String SQL_SELECT_FROM_COUNTRIES = "SELECT * FROM COUNTRIES WHERE country = ?";

	/**
	 * 
	 * @param headers
	 */
	public SqlFile(final String[] headers) {
		this.headers = headers;
		this.countryIndex = this.searchHeaderIndex("country");
		this.firstnameIndex = this.searchHeaderIndex("first_name");
		this.lastnameIndex = this.searchHeaderIndex("last_name");
		try {
			this.getNewConnection();
			this.createTableCountries();
			this.createTableUsers();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.newCountry = true;
	}

	/**
	 * 
	 * @param headerName
	 * @return headerIndex
	 */
	public int searchHeaderIndex(final String headerName) {
		int headerIndex = -1;
		for (int i = 0; i < this.headers.length; i++) {
			if (this.headers[i].equals(headerName)) {
				headerIndex = i;
				break;
			}
		}
		return headerIndex;
	}
	
	private void getNewConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		String url = "jdbc:h2:mem:test";
		String user = "sa";
		String passwd = "sa";
		this.conn = DriverManager.getConnection(url, user, passwd);
	}

	private void closeConnection() throws SQLException {
		this.conn.close();
	}

	private void createTableCountries() throws SQLException {
		Statement st = this.conn.createStatement();
		st.executeUpdate(this.SQL_CREATE_COUNTRIES);
		st.close();
	}

	private void createTableUsers() throws SQLException {
		Statement st = this.conn.createStatement();
		st.executeUpdate(this.SQL_CREATE_USERS);
		st.close();
	}

	/**
	 * 
	 * @param country
	 * @throws SQLException 
	 */
	public void insertIntoCountries(final String country) throws SQLException {
		PreparedStatement pst = this.conn.prepareStatement(SQL_INSERT_COUNTRIES);
		pst.setString(1, country);
		pst.execute();
		pst.close();
	}

	/**
	 * 
	 * @param user
	 * @throws SQLException 
	 */
	public void insertIntoUsers(final String[] user) throws SQLException {
		PreparedStatement pst = this.conn.prepareStatement(SQL_INSERT_USERS);
		pst.setString(1, user[0]);
		pst.setString(2, user[1]);
		pst.execute();
		pst.close();
	}
	
	/**
	 * 
	 * @param line
	 * @throws SQLException 
	 * @throws IOException
	 */
	public void saveIntoTables(final String[] line) throws SQLException {
		if (this.newCountry) {
			this.newCountry = false;
			this.thisCountry = line[countryIndex];
			this.lastCountry = line[countryIndex];
		}
		if (!lastCountry.equals(line[countryIndex])) {
			this.insertIntoCountries(lastCountry);
			this.selectFromCountries(lastCountry);
			this.newCountry = true;
		}
		String[] elements = new String[2];
		elements[0] = line[firstnameIndex];
		elements[1] = line[lastnameIndex];
		this.insertIntoUsers(elements);
		this.lastCountry = line[countryIndex];
		if (line[0].equals("-")) {
			this.closeConnection();
		}
	}
	/**
	 * 
	 * @param country
	 * @throws SQLException
	 */
	public void selectFromCountries(final String country) throws SQLException {
		PreparedStatement pst = this.conn.prepareStatement(this.SQL_SELECT_FROM_COUNTRIES);
		pst.setString(1, country);
		pst.execute();
		ResultSet rs = pst.getResultSet();
		rs.next();
//		System.out.println("id = " + rs.getInt(1) + " |  country = " + rs.getString(2));
		pst.close();	
	}
	
	/**
	 * 
	 * @param line
	 * @return void
	 */
	public CompletableFuture<Void> saveIntoTablesFuture(final String[] line) {
		return CompletableFuture.runAsync(() -> {
				try {
					this.saveIntoTables(line);
				} catch (SQLException e) {
					e.printStackTrace();
				}
		});
	}
}
