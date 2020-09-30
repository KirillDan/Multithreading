package ru.job4j.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

interface ParseFileReader {
	public String getContent(final boolean withoutUnicode, final File file);
}

interface ParseFileWriter {
	public void saveContent(final String content, final File file);
}

class ParseFileReaderImpl implements ParseFileReader {
	/**
	 * Read file string content may be without unicode.
	 * 
	 * @param file
	 * @param withoutUnicode
	 * @return String output
	 */
	public synchronized String getContent(final boolean withoutUnicode, final File file) {
		String result = null;
		String output = null;
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
		if (withoutUnicode) {
			ByteArrayInputStream i = new ByteArrayInputStream(output.getBytes());
			int data;
			StringBuilder sb = new StringBuilder("");
			while ((data = i.read()) > 0) {
				if (data < 0x80) {
					sb.append((char) data);
				}
			}
			result = sb.toString();
		} else {
			result = output;
		}
		return result;
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
	 * Read file string content may be without unicode.
	 * 
	 * @param withoutUnicode
	 * @return String output
	 */
	public synchronized String getContent(final boolean withoutUnicode) {
		return parseFileReader.getContent(withoutUnicode, this.file);
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
