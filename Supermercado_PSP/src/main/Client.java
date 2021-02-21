package main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private static String host = "localhost";
	private static int port = 6060;
	private static Scanner teclado = new Scanner(System.in);
	private static Socket client;
	private static DataOutputStream dataOutputStream;
	private static ObjectInputStream objectInputStream;
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
			// Bucle del menú de cobros, obtener caja y salir
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
	 * iniciar sesión en la aplicacion
	 * @throws IOException
	 * @throws ClassNotFoundException 
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
			objectInputStream = new ObjectInputStream(client.getInputStream());
			try {
				if(!objectInputStream.readObject().equals(null)) {
					isLoginCorrect = true;
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		} catch (NumberFormatException nfe) {
			
		}
	}
	
	public static void menu() throws IOException {
		System.out.println("\n¿Qué desea realizar?");
		System.out.println("1. Cobrar compra");
		System.out.println("2. Obtener la caja del día");
		System.out.println("0. Salir");
		String option = teclado.next();
		try {
			int op = Integer.parseInt(option);
			switch(op) {
			case 1:
				purchaseOne();
				purchaseTwo();
				break;
			case 2:
				result();
				break;
			case 0:
				closeServerThread();
				client.close();
				System.exit(0);
				break;
			}
		}catch (NumberFormatException nfe) {
			
		}
	}
	
	public static void purchaseOne() throws IOException {
		System.out.println("Lista de Productos:");
		dataOutputStream = new DataOutputStream(client.getOutputStream());
		dataOutputStream.writeUTF("productos");
		objectInputStream = new ObjectInputStream(client.getInputStream());
		try {
			Object countProduct = objectInputStream.readObject();
			int counter = Integer.parseInt(countProduct.toString());
			for(int i = 0; i < counter ; i++) {
				System.out.println(objectInputStream.readObject());
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void purchaseTwo() {
		boolean answerCorrect = false;
		while(!answerCorrect) {
			System.out.print("Seleccione el artículo que desea: ");
			String productSelectString = teclado.next();
			System.out.print("¿Cuántas unidades? ");
			String productAmountString = teclado.next();
			try {
				int productSelect = Integer.parseInt(productSelectString);
				int productAmount = Integer.parseInt(productAmountString);
				answerCorrect = true;
				try {
					dataOutputStream = new DataOutputStream(client.getOutputStream());
					dataOutputStream.writeUTF("cobro;" + productSelect + ";" + productAmount);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Respuesta incorrecta, escriba solo con dígitos");
			}
		}
	}
	
	public static void result() throws IOException {
		System.out.println("La caja del día:");
		dataOutputStream = new DataOutputStream(client.getOutputStream());
		dataOutputStream.writeUTF("caja");
		objectInputStream = new ObjectInputStream(client.getInputStream());
		try {
			Object countProducts = objectInputStream.readObject();
			int counter = Integer.parseInt(countProducts.toString());
			for (int i = 0; i < counter; i++) {
				
				System.out.println(objectInputStream.readObject());
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
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