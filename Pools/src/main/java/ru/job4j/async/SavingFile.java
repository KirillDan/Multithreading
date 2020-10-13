package ru.job4j.async;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;

/**
 * 
 * @author kirill
 *
 */
public class SavingFile {

	/**
	 * 
	 * @param user
	 * @throws IOException
	 */
	public void saveToFileCountry(final User user) throws IOException {
		File file = new File("Countries/" + user.getCountry() + ".txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		StringBuilder sb = new StringBuilder("");
			sb.append(user.getFirstName()).append("  ")
			.append(user.getLastName()).append("  ")
			.append(user.getRating());
			sb.append("\n");
		Files.writeString(file.toPath(), sb, StandardOpenOption.APPEND);
	}

	/**
	 * 
	 * @param user
	 * @return void
	 */
	public CompletableFuture<Void> usersByCountriesFuture(final User user) {
		return CompletableFuture.runAsync(() -> {
			try {
				this.saveToFileCountry(user);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
