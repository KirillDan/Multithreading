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
	public SocketFile() throws UnknownHostException, IOException {
		super();
		this.createSocket();
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
	 * @param user
	 * @throws IOException 
	 */
	public void postInSocket(final User user) throws IOException {
			String message = user.getFirstName() + "  " + user.getLastName() + "  " 
					+ user.getCountry() + "  " + user.getRating() + "\n";
			this.out.write(message.getBytes());

	}
	/**
	 * 
	 * @param user
	 * @return void
	 */
	public CompletableFuture<Void> postInSocketFuture(final User user) {
		return CompletableFuture.runAsync(() -> {
					try {
						this.postInSocket(user);
					} catch (IOException e) {
						e.printStackTrace();
						this.closeSocket();
					}
		});
	}
	
}
