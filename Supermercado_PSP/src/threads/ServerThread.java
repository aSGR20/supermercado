package threads;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import dao.*;
import models.*;

public class ServerThread extends Thread{
	
	private static Socket _clientConnection = null;
	private static DataInputStream dataInputStream;
	private static ObjectOutputStream objectOutputStream;
	private static String message;
	private static String[] partsMessage;
	private static DAO_Employee employeeDao;
	private static DAO_Product productDao;
	private static DAO_Purchase purchaseDao;
	
	public ServerThread (Socket clientConnection) {
		_clientConnection = clientConnection;
	}
	
	public void run () {
		// Inicializacion de los DAO
		employeeDao = new DAO_Employee();
		productDao = new DAO_Product();
		purchaseDao = new DAO_Purchase();
		
		// Mientras no se haya cerrado la conexion con el cliente
		while(!_clientConnection.isClosed()) {
			// Descrifra el mensaje recibido por parte del cliente y dependiendo
			// del mensaje principal, realiza un método u otro
			try {
				dataInputStream = new DataInputStream(_clientConnection.getInputStream());
				partsMessage = dataInputStream.readUTF().split(";");
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(!partsMessage.equals(null)) {
				if (partsMessage[0].equals("login")) {
					try {
						if(getID()) {
							objectOutputStream = new ObjectOutputStream(_clientConnection.getOutputStream());
							objectOutputStream.writeObject(employeeDao.getEmployeeById(Integer.parseInt(partsMessage[1])));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (partsMessage[0].equals("productos")) {
					try {
						objectOutputStream = new ObjectOutputStream(_clientConnection.getOutputStream());
						objectOutputStream.writeObject(productDao.getCountProduct());
						for(Product product : productDao.getProduct()) {
							objectOutputStream.writeObject(product.toString());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (partsMessage[0].equals("cobro")) {
					
				} else if (partsMessage[0].equals("ver")) {
					
				} else if (partsMessage[0].equals("exit")) {
					try {
						dataInputStream.close();
						objectOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
		}
	}
	
	/**
	 * Si el mensaje principal obtiene "login", comprueba en la base de datos <br>
	 * si la id existe o no
	 * @throws IOException
	 */
	public static boolean getID() throws IOException {
		message = partsMessage[1];
		for (Employee employee : employeeDao.getEmployee()) {
			if(Integer.parseInt(message) == employee.get_id()) {
				return true;
			}
		}
		return false;
	}
	
	public static Object getProduct() {
		return null;
	}
	
	public static void getPurchase() {
		
	}
}
