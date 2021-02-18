package main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	static String host = "localhost";
	static int port = 6060;
	
	public static void main(String[] args) {
		try {
			System.out.println("Client initializing");
			Socket client = new Socket(host, port);
			System.out.println("Client ready");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}