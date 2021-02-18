package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import threads.*;

public class Server {
	
	static int port = 6060;
	int idClient = 0;

	public static void main(String[] args) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				//while(true) {
					try {
						System.out.println("server initializing");
						ServerSocket server = new ServerSocket(port);
						System.out.println("server ready");
						Socket clientConnection = null;
						clientConnection = server.accept();
						ServerThread thread = new ServerThread(clientConnection);
						thread.start();
						System.out.println("server finished");	
					} catch (IOException e) {
						System.out.println(e);
					}
					
				}
			//}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
}
