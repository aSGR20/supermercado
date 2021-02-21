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
	private static int idEmployee;
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
		
		while(!_clientConnection.isClosed()) {
			try {
				dataInputStream = new DataInputStream(_clientConnection.getInputStream());
				partsMessage = dataInputStream.readUTF().split(";");
				if(!partsMessage[0].equals("exit")) {
					menu();
				} else {
					_clientConnection.close();
					dataInputStream.close();
					objectOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void menu() {
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
					getProduct();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (partsMessage[0].equals("cobro")) {
				try {
					updateProduct();
				} catch(IOException e) {
					e.printStackTrace();
				}
			} else if (partsMessage[0].equals("caja")) {
				try {
					getPurchase();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean getID() throws IOException {
		message = partsMessage[1];
		for (Employee employee : employeeDao.getEmployee()) {
			if(Integer.parseInt(message) == employee.get_id()) {
				idEmployee = Integer.parseInt(message);
				return true;
			}
		}
		return false;
	}
	
	public static void getProduct() throws IOException {
		objectOutputStream = new ObjectOutputStream(_clientConnection.getOutputStream());
		objectOutputStream.writeObject(productDao.getCountProduct());
		for(Product product : productDao.getProduct()) {
			objectOutputStream.writeObject(product.toString());
		}
	}
	
	public static void updateProduct() throws IOException {
		int idProduct = Integer.parseInt(partsMessage[1]);
		int amountProduct = Integer.parseInt(partsMessage[2]);
		if(amountProduct <= productDao.getStockProduct(idProduct)) {
			productDao.updateProduct(idProduct, amountProduct);
			purchaseDao.insertPurchase(idProduct, idEmployee);
		} else {
			// JAVAMAIL
		}
	}
	
	public static void getPurchase() throws IOException{
		objectOutputStream = new ObjectOutputStream(_clientConnection.getOutputStream());
		objectOutputStream.writeObject(productDao.getCountProduct());
		// OBTENER LA DIFERENCIA DE LOS PRODUCTOS
		for (Purchase purchase : purchaseDao.getPurchaseToday()) {
			objectOutputStream.writeObject(purchase.toString());
		}
	}
}
