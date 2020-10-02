package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This class outputs to console names of threads.
 * 
 * @author kirill
 */
public class FileDownload implements Callable<ByteArrayOutputStream> {
	private final String fileUrl;
	private final int rate;

	/**
	 * 
	 * @param fileUrl
	 * @param rate
	 */
	public FileDownload(final String fileUrl, final int rate) {
		this.fileUrl = fileUrl;
		this.rate = rate;
	}

	@Override
	public ByteArrayOutputStream call() throws Exception {
		long startTime;
		long stopTime;
		float kBytes = 0;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream())) {
			byte[] dataBuffer = new byte[1024];
			int bytesRead;
			startTime = System.currentTimeMillis();
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				out.write(dataBuffer, 0, bytesRead);
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
		return out;
	}

	/**
	 * Main method.
	 * 
	 * @param args console inputs.
	 */
	public static void main(final String[] args) {
		String fileUrl = args[0];
		int rate = Integer.parseInt(args[1]);
		Future<ByteArrayOutputStream> fb;
		ExecutorService es = Executors.newSingleThreadExecutor();
		fb = es.submit(new FileDownload(fileUrl, rate));
		ByteArrayOutputStream resultOutputStream = null;
		try {
			resultOutputStream = fb.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		try (FileOutputStream fout = new FileOutputStream("SavedFile" + new Random().nextInt())) {
			resultOutputStream.writeTo(fout);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		es.shutdown();
	}
}
