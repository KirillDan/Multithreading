package ru.job4j.async;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class User {
	private String firstName;
	private String lastName;
	private String country;
	private Integer rating;
	public User(final String firstName, final String lastName, final String country, final Integer rating) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.rating = rating;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getCountry() {
		return country;
	}
	public Integer getRating() {
		return rating;
	}
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName 
				+ ", country=" + country + ", rating=" + rating + "]";
	}
}


/**
 * 
 * @author kirill
 *
 */
public class ParsingSourceFile {
	private File file;
	private SavingFile savingFile;
	private SqlFile sqlFile;
	private SocketFile socketFile;
	/**
	 * 
	 * @param file
	 */
	public ParsingSourceFile(final File file) {
		super();
		this.file = file;
		this.savingFile = new SavingFile();
		this.sqlFile = new SqlFile();
		try {
			this.socketFile = new SocketFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param string
	 * @return line
	 */
	public User parseFile(final String string) {
		User user;
		String[] elements = string.split("  ");
		user = new User(elements[0], elements[1], elements[2], Integer.valueOf(elements[3]));
		return user;	
	}
	
	/**
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * 
	 */
	public void readLinesFile() throws InterruptedException, ExecutionException, SQLException {
		try {
			List<String> lines = Files.readAllLines(this.file.toPath());
			boolean headerOut = false;
			for (String string : lines) {
				if (!headerOut) {
					headerOut = true;
					continue;
				}
				User user = this.parseFile(string);
				CompletableFuture<Void> vsf = this.savingFile.usersByCountriesFuture(user);
				CompletableFuture<Void> vsql = this.sqlFile.saveIntoTablesFuture(user);
				CompletableFuture<Void> vs = this.socketFile.postInSocketFuture(user);
				
				vsf.get();
				vsql.get();
				vs.get();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		sqlFile.closeConnection();
		socketFile.closeSocket();
	}

	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws SQLException 
	 */
	public static void main(final String[] args) throws IOException, 
	InterruptedException, ExecutionException, SQLException {
		ParsingSourceFile parsingSourceFile = new ParsingSourceFile(new File("source.txt"));
		parsingSourceFile.readLinesFile();
		System.out.println("End");
	}
}
