package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private static String host = "localhost";
	private static int port = 6060;
	private static Scanner teclado = new Scanner(System.in);
	private static Socket client;
	private static DataOutputStream dataOutputStream;
	private static DataInputStream dataInputStream;
	private static boolean isLoginCorrect = false;
	private static boolean continuous = true;
	
	public static void main(String[] args) {
		try {
			// Conexion con el servidor
			client = new Socket(host, port);
			login();
			while(!isLoginCorrect) {
				System.out.println("\tID del empleado Incorrecto\n");
				login();
			}
			// Bucle del men� de cobros, obtener caja y salir
			while(continuous) {
				menu();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que pide por pantalla el ID del empleado con el que cual <br>
	 * iniciar sesi�n en la aplicacion
	 * @throws IOException
	 */
	public static void login() throws IOException {
		// Pide la ID del empleado
		System.out.print("Introduzca su id: ");
		String idTeclado = teclado.next();
		try {
			int id = Integer.parseInt(idTeclado);
			// Envia la ID al servidor
			dataOutputStream = new DataOutputStream(client.getOutputStream());
			dataOutputStream.writeUTF("login;"+ id);
			
			// Recibe true or false si el ID existe en la BBDD
			dataInputStream = new DataInputStream(client.getInputStream());
			isLoginCorrect = dataInputStream.readBoolean();
		} catch (NumberFormatException nfe) {
			
		}
	}
	
	public static void menu() throws IOException {
		System.out.println("\n�Qu� desea realizar?");
		System.out.println("1. Cobrar compra");
		System.out.println("2. Obtener la caja del d�a");
		String option = teclado.next();
		try {
			int op = Integer.parseInt(option);
			switch(op) {
			case 1:
				charge();
				break;
			case 2:
				result();
				break;
			case 0:
				closeServerThread();
				client.close();
				break;
			}
		}catch (NumberFormatException nfe) {
			
		}
	}
	
	public static void charge() {
		
		try {
			dataOutputStream = new DataOutputStream(client.getOutputStream());
			dataOutputStream.writeUTF(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void result() {
		
	}
	
	/**
	 * Metodo el cual cierra la conexion que tiene con el hilo del servidor <br>
	 * IMPORTANTE! CERRAR ANTES DE CERRAR LA CONEXION DEL INTERPRETE
	 * @throws IOException
	 */
	public static void closeServerThread() throws IOException {
		dataOutputStream = new DataOutputStream(client.getOutputStream());
		dataOutputStream.writeUTF("exit");
	}
}