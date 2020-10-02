package ru.job4j.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Predicate;

interface ParseFileReader {
	public String getContent(final Predicate<Integer> pred, final File file);
}

interface ParseFileWriter {
	public void saveContent(final String content, final File file);
}

class ParseFileReaderImpl implements ParseFileReader {
	/**
	 * Read file string content may be without unicode.
	 * 
	 * @param file
	 * @param pred
	 * @return String output
	 */
	public synchronized String getContent(final Predicate<Integer> pred, final File file) {
		StringBuilder output = new StringBuilder("");
		try (FileInputStream i = new FileInputStream(file)) {
			int data;
			while ((data = i.read()) > 0) {
				if (pred.test(data)) {
					output.append((char) data);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output.toString();
	}
}

class ParseFileWriterImpl implements ParseFileWriter {
	/**
	 * Saving file string content.
	 * 
	 * @param file
	 * @param content
	 */
	public synchronized void saveContent(final String content, final File file) {
		try {
			FileOutputStream o = new FileOutputStream(file, true);
			o.write(content.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/**
 * This class demonstrate multithread file parser.
 * 
 * @author kirill
 */
public class ParseFile {
	private volatile File file;
	private ParseFileReader parseFileReader;
	private ParseFileWriter parseFileWriter;

	/**
	 * Setting file content reader for parsing.
	 * 
	 * @param parseFileReader
	 */
	public void setParseFileReader(final ParseFileReader parseFileReader) {
		this.parseFileReader = parseFileReader;
	}

	/**
	 * Setting file content writer.
	 * 
	 * @param parseFileWriter
	 */
	public void setParseFileWriter(final ParseFileWriter parseFileWriter) {
		this.parseFileWriter = parseFileWriter;
	}

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
	 */
	public synchronized String getContent() {
		return parseFileReader.getContent(i -> true, this.file);
	}

	/**
	 * Read file string content without unicode.
	 * 
	 * @return String output
	 */
	public synchronized String getContentWithoutUnicode() {
		return parseFileReader.getContent(i -> i < 0x80, this.file);
	}

	/**
	 * Saving file string content.
	 * 
	 * @param content
	 */
	public synchronized void saveContent(final String content) {
		parseFileWriter.saveContent(content, this.file);
	}
}
