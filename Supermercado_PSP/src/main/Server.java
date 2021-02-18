package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import threads.*;

public class Server {
	
	private static int port = 6060;
	private int idClient = 0;
	private ServerSocket server;

	public static void main(String[] args) throws IOException {
		System.out.println("Server iniciado");
		// Inicializo el server socket
		ServerSocket server = new ServerSocket(port);
		// Creo un hilo el cuál va a estar escuchando las peticiones al servidor
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while(true) {
						// Inicializo la conexion con el cliente
						Socket clientConnection = null;
						// Espero a obtener la conexion con el cliente
						try {
							clientConnection = server.accept();
						} catch (IOException e) {
							e.printStackTrace();
						}
						// Una vez obtenido la conexion con el cliente, la paso a un hilo
						ServerThread thread = new ServerThread(clientConnection);
						// Inicializo el hilo
						thread.start();					
				}
			}
		};
		// Lanza el hilo que escuchará las peticiones del servidor
		Thread thread = new Thread(runnable);
		thread.start();
	}
}
