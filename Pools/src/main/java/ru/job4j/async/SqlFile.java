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
	public SqlFile() {
		try {
			this.getNewConnection();
			this.createTableCountries();
			this.createTableUsers();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getNewConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		String url = "jdbc:h2:mem:test";
		String user = "sa";
		String passwd = "sa";
		this.conn = DriverManager.getConnection(url, user, passwd);
	}
	/**
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException {
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
	 * @param user
	 * @throws SQLException
	 */
	public void insertIntoCountries(final User user) throws SQLException {
		PreparedStatement pst = this.conn.prepareStatement(SQL_INSERT_COUNTRIES);
		pst.setString(1, user.getCountry());
		pst.execute();
		pst.close();
	}

	/**
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void insertIntoUsers(final User user) throws SQLException {
		PreparedStatement pst = this.conn.prepareStatement(SQL_INSERT_USERS);
		pst.setString(1, user.getFirstName());
		pst.setString(2, user.getLastName());
		pst.execute();
		pst.close();
	}

	/**
	 * 
	 * @param user
	 * @throws SQLException
	 * @throws IOException
	 */
	public void saveIntoTables(final User user) throws SQLException {
		this.insertIntoCountries(user);
//		this.selectFromCountries(user.getCountry());
		this.insertIntoUsers(user);
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
		System.out.println("id = " + rs.getInt(1) + " |  country = " + rs.getString(2));
		pst.close();
	}

	/**
	 * 
	 * @param user
	 * @return void
	 */
	public CompletableFuture<Void> saveIntoTablesFuture(final User user) {
		return CompletableFuture.runAsync(() -> {
			try {
				this.saveIntoTables(user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
}
