package ru.job4j.async;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author kirill
 *
 */
public class ServerSocketFile implements Runnable {
	private Socket socket;
	/**
	 * 
	 */
	public ServerSocketFile(final Socket socket) {
		this.socket = socket;
	}
	/**
	 * 
	 */
	public void closeSocket() {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		InputStream is = null;
		try {
			is = this.socket.getInputStream();
			int c;
			String result = "";
			String endMessage = "\n\n";
			byte[] last4Bytes = new byte[4];
			while ((is.read(last4Bytes)) != -1) {	
				String message = new String(last4Bytes);
				if (message.equals(endMessage)) {
					break;
				}
				result += message;
				System.out.print(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
			this.closeSocket();
		} 
		
	}
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(final String[] args) throws IOException {
		try (ServerSocket ss = new ServerSocket(8080)) {
			while (true) {
				new Thread(new ServerSocketFile(ss.accept())).start();
			}
			
		}

	}
}
