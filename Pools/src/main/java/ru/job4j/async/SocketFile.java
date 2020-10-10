package ru.job4j.async;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CompletableFuture;

/**
 * 
 * @author kirill
 *
 */
public class SocketFile {
	private Socket socket;
	private OutputStream out;
	/**
	 * 
	 * @param headers
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public SocketFile(final String[] headers) throws UnknownHostException, IOException {
		super();
		this.createSocket();
		this.postInSocket(headers);
	}
	/**
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * 
	 */
	public void createSocket() throws UnknownHostException, IOException {
		this.socket = new Socket("localhost", 8080);
		this.out =  this.socket.getOutputStream();
	}
	/**
	 * 
	 * @throws IOException
	 */
	public void closeSocket() {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param line
	 * @throws IOException 
	 */
	public void postInSocket(final String[] line) throws IOException {
		if (line[0] != "-") {
			String message = line[0] + "  " + line[1] + "  " + line[2] + "  " + line[3] + "\n";
			this.out.write(message.getBytes());
		} else {
			String endMessage = "\n\n";
			this.out.write(endMessage.getBytes());
			this.closeSocket();
		}
	}
	/**
	 * 
	 * @param line
	 * @return void
	 */
	public CompletableFuture<Void> postInSocketFuture(final String[] line) {
		return CompletableFuture.runAsync(() -> {
					try {
						this.postInSocket(line);
					} catch (IOException e) {
						e.printStackTrace();
						this.closeSocket();
					}
		});
	}
	
}
