package main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import threads.*;

public class Client {

	private static String host = "localhost";
	private static int port = 6060;
	private static Scanner teclado = new Scanner(System.in);
	private static Socket client;
	
	public static void main(String[] args) {
		try {
			// Conexion con el servidor
			client = new Socket(host, port);
			// Una vez realizada la conexion con el servidor, se le pide el nº identificativo
			login();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void login() throws IOException {
		// Pide la ID del empleado
		System.out.print("Introduzca su id: ");
		String id = teclado.next();
		// Envia la ID al servidor
		DataOutputStream flujoSalida = new DataOutputStream(client.getOutputStream());
		flujoSalida.writeUTF("login;"+ id);
	}
}