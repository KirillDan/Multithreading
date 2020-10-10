package ru.job4j.async;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 
 * @author kirill
 *
 */
public class ParsingSourceFile {
	private File file;
	private String[] headers;
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
	}
	/**
	 * @param string
	 * @return line
	 */
	public String[] parseFile(final String string) {
		String[] resultElements = new String[4];
		String[] elements = string.split("  ");
		int resultElementsIndex = -1;
		for (int i = 0; i < elements.length; i++) {
			elements[i] = elements[i].trim();
			if (!elements[i].isEmpty()) {
				resultElementsIndex += 1;
				resultElements[resultElementsIndex] = elements[i];
			}
		}	
		return resultElements;	
	}
	
	/**
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * 
	 */
	public void readLinesFile() throws InterruptedException, ExecutionException {
		try {
			List<String> lines = Files.readAllLines(this.file.toPath());
			lines.add("-  -  -  -");
			boolean headerOut = false;
			for (String string : lines) {
				if (!headerOut) {
					headerOut = true;
					this.headers = this.parseFile(string);
					this.savingFile = new SavingFile(headers);
					this.sqlFile = new SqlFile(headers);
					this.socketFile = new SocketFile(headers);
					continue;
				}
				String[] res = this.parseFile(string);
				CompletableFuture<Void> vsf = this.savingFile.usersByCountriesFuture(res);
				CompletableFuture<Void> vsql = this.sqlFile.saveIntoTablesFuture(res);
				CompletableFuture<Void> vs = this.socketFile.postInSocketFuture(res);
				
				vsf.get();
				vsql.get();
				vs.get();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("");
			for (int i = 0; i < this.headers.length; i++) {
				result.append(this.headers[i]).append("  ");
			}
			result.append("\n");
		return result.toString();
	}

	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(final String[] args) throws IOException, InterruptedException, ExecutionException {
		ParsingSourceFile parsingSourceFile = new ParsingSourceFile(new File("source.txt"));
		parsingSourceFile.readLinesFile();
		System.out.println("End");
	}
}
