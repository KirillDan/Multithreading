package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * This class outputs to console names of threads.
 * @author kirill
 */
public class FileDownload {
	protected FileDownload() {
	}
	/**
	 * Main method.
	 * @param args console inputs.
	 */
	public static void main(final String[] args) {
		String fileUrl = args[0];
		int rate = Integer.parseInt(args[1]);
		long startTime;
		long stopTime;
		float kBytes = 0;
		try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
				FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp3.xml")) {
			byte[] dataBuffer = new byte[1024];
			int bytesRead;			
			startTime = System.currentTimeMillis();
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				fileOutputStream.write(dataBuffer, 0, bytesRead);
				kBytes += 1f;
				try {
					stopTime = System.currentTimeMillis();
					if (stopTime - startTime >= 1000) {
						if (kBytes > rate) {
							Thread.sleep((long) ((kBytes / rate) * 1000));
						}
						startTime = System.currentTimeMillis();
						kBytes = 0;
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
