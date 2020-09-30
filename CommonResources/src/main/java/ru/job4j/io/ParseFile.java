package ru.job4j.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class demonstrate multithread file parser.
 * 
 * @author kirill
 */
public class ParseFile {
	private volatile File file;

	/**
	 * Setting file for parsing.
	 * 
	 * @param f
	 */
	public synchronized void setFile(final File f) {
		file = f;
	}

	/**
	 * Get file.
	 * 
	 * @return File
	 */
	public synchronized File getFile() {
		return file;
	}

	/**
	 * Read file string content.
	 * 
	 * @return String output
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public synchronized String getContent() {
		String output = "";
		try (FileInputStream i = new FileInputStream(file)) {
			int fileSize = i.available();
			byte[] data = new byte[fileSize];
			if (i.read(data) == fileSize) {
				output = new String(data, 0, fileSize);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	/**
	 * Read file string content without unicode.
	 * 
	 * @return String output
	 * @throws IOException
	 */
	public synchronized String getContentWithoutUnicode() {
		String output = this.getContent();
		ByteArrayInputStream i = new ByteArrayInputStream(output.getBytes());
		int data;
		StringBuilder sb = new StringBuilder("");
		while ((data = i.read()) > 0) {
				if (data < 0x80) {
					sb.append((char) data);
				}
		}
		return sb.toString();
	}

	/**
	 * Saving file string content.
	 * 
	 * @param content
	 * @throws IOException
	 */
	public synchronized void saveContent(final String content) {
		try {
			FileOutputStream o = new FileOutputStream(file);
			o.write(content.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
