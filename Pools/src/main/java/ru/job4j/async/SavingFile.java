package ru.job4j.async;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 
 * @author kirill
 *
 */
public class SavingFile {
	private String[] headers;
	private int countryIndex;
	private int firstnameIndex;
	private int lastnameIndex;
	private int ratingIndex;
	private List<String[]> countryPeoples;
	private boolean newCountry;
	private String thisCountry;
	private String lastCountry;

	/**
	 * 
	 * @param headers
	 */
	public SavingFile(final String[] headers) {
		this.headers = headers;
		this.countryIndex = this.searchHeaderIndex("country");
		this.firstnameIndex = this.searchHeaderIndex("first_name");
		this.lastnameIndex = this.searchHeaderIndex("last_name");
		this.ratingIndex = this.searchHeaderIndex("rating");
		this.countryPeoples = new ArrayList<String[]>();
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

	/**
	 * @param strings
	 * @param thisCountry
	 * @throws IOException
	 */
	/**
	 * 
	 * @param countryPeoples
	 * @param thisCountry
	 * @throws IOException
	 */
	public void saveToFileCountry(
			final List<String[]> countryPeoples, final String thisCountry) throws IOException {
		File file = new File("Countries/" + thisCountry + ".txt");
		file.createNewFile();
		StringBuilder sb = new StringBuilder("");
		for (String[] strings : countryPeoples) {
			for (int i = 0; i < strings.length; i++) {
				sb.append(strings[i]);
				if (i < strings.length - 1) {
					sb.append(" ");
				}
			}
			sb.append("\n");
		}
		Files.writeString(file.toPath(), sb);
	}

	/**
	 * 
	 * @param line
	 * @throws IOException
	 */
	public void usersByCountries(final String[] line) throws IOException {
		if (this.newCountry) {
			this.newCountry = false;
			this.thisCountry = line[countryIndex];
			this.lastCountry = line[countryIndex];
		}
		if (!lastCountry.equals(line[countryIndex])) {
			saveToFileCountry(this.countryPeoples, this.thisCountry);
			this.countryPeoples.clear();
			this.newCountry = true;
		}
		String[] elements = new String[3];
		elements[0] = line[firstnameIndex];
		elements[1] = line[lastnameIndex];
		elements[2] = line[ratingIndex];
		this.countryPeoples.add(elements);
		this.lastCountry = line[countryIndex];
	}

	/**
	 * 
	 * @param line
	 * @return void
	 */
	public CompletableFuture<Void> usersByCountriesFuture(final String[] line) {
		return CompletableFuture.runAsync(() -> {
			try {
				this.usersByCountries(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
