package threads;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.*;
import models.*;
import controller.*;

public class ServerThread extends Thread{
	
	private static Socket _clientConnection = null;
	private static DataInputStream dataInputStream;
	private static ObjectOutputStream objectOutputStream;
	private static String message;
	private static String email;
	private static int idEmployee;
	private static String[] partsMessage;
	private static DAO_Employee employeeDao;
	private static DAO_Product productDao;
	private static DAO_Purchase purchaseDao;
	private static Controller_XML xmlController;
	
	public ServerThread (Socket clientConnection) {
		_clientConnection = clientConnection;
	}
	
	public void run () {
		// Inicializacion de los DAO
		employeeDao = new DAO_Employee();
		productDao = new DAO_Product();
		purchaseDao = new DAO_Purchase();
		xmlController = new Controller_XML();
		email = xmlController.getCorreo();
		
		// Mientras la conexion con el cliente no se haya cerrado, el servidor estará escuchando
		while(!_clientConnection.isClosed()) {
			try {
				dataInputStream = new DataInputStream(_clientConnection.getInputStream());
				partsMessage = dataInputStream.readUTF().split(";");
				if(!partsMessage[0].equals("exit")) {
					menu();
				} else {
					updateEmployee();
					_clientConnection.close();
					dataInputStream.close();
					objectOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Menu que analiza el mensaje recibido y dependiendo de lo que contenga, llama a un metodo u otro
	 */
	public static void menu() {
		if(!partsMessage.equals(null)) {
			if (partsMessage[0].equals("login")) {
				try {
					if(getID()) {
						objectOutputStream = new ObjectOutputStream(_clientConnection.getOutputStream());
						objectOutputStream.writeObject(employeeDao.getEmployeeById(Integer.parseInt(partsMessage[1])));
					} else {
						objectOutputStream = new ObjectOutputStream(_clientConnection.getOutputStream());
						objectOutputStream.writeObject(null);
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
	
	/**
	 * Metodo que obtiene la ID y en caso de existir, devuelve un true
	 * @return
	 * @throws IOException
	 */
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
	
	/**
	 * Metodo que obtiene los productos que existen y los envia al cliente
	 * @throws IOException
	 */
	public static void getProduct() throws IOException {
		objectOutputStream = new ObjectOutputStream(_clientConnection.getOutputStream());
		objectOutputStream.writeObject(productDao.getCountProduct());
		for(Product product : productDao.getProduct()) {
			objectOutputStream.writeObject(product.toString());
		}
	}
	
	/**
	 * Metodo que actualiza la cantidad de stock sacada <br>
	 * e introduce la compra realizada
	 * @throws IOException
	 */
	public static void updateProduct() throws IOException {
		int idProduct = Integer.parseInt(partsMessage[1]);
		int amountProduct = Integer.parseInt(partsMessage[2]);
		if(amountProduct <= productDao.getStockProduct(idProduct)) {
			productDao.updateProduct(idProduct, amountProduct);
			purchaseDao.insertPurchase(idProduct, amountProduct, idEmployee);
		} else {
			java.util.Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("HH-mm");
			Controller_JavaMail javaMailController = new Controller_JavaMail();
			javaMailController.sendEmail(email, productDao.getNameProduct(idProduct), "El producto \"" + productDao.getNameProduct(idProduct) + 
					"\" se ha quedado sin stock a las " + dateFormat.format(date) + ". Le recordamos que el precio del producto es de: " + productDao.getPriceSupplierProduct(idProduct) + "€.");
		}
	}
	
	/**
	 * Metodo que obtiene las compras realizadas hoy y <br>
	 * envia al cliente las compras
	 * @throws IOException
	 */
	public static void getPurchase() throws IOException{
		objectOutputStream = new ObjectOutputStream(_clientConnection.getOutputStream());
		objectOutputStream.writeObject(purchaseDao.getCountPurchaseToday());
		for (Purchase purchase : purchaseDao.getPurchaseToday()) {
			Product product = productDao.getNameAndDifferenceById(purchase.get_idProduct());
			int difference = (product.get_precioVenta() - product.get_precioProveedor()) * purchase.get_amountProduct();
			objectOutputStream.writeObject("Compra nº " + purchase.get_id() + "\t||" + "Producto: " + product.get_nombreProducto() + "\t||" + "\tPrecio: " + difference);
		}
	}
	
	/**
	 * Actualiza la ultima hora de empleado
	 * @throws IOException
	 */
	public static void updateEmployee() throws IOException {
		employeeDao.insertLastSession(idEmployee);
	}
}