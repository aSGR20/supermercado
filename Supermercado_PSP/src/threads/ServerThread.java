package threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import dao.*;
import models.*;

public class ServerThread extends Thread{
	
	private static Socket _clientConnection = null;
	private static InputStream inputStream = null;
	private static OutputStream outputStream = null;
	private static DataInputStream dataInputStream;
	private static DataOutputStream dataOutputStream;
	private static String message;
	private static String[] partsMessage;
	private static DAO_Employee employeeDao;
	
	public ServerThread (Socket clientConnection) {
		_clientConnection = clientConnection;
	}
	
	public void run () {
		employeeDao = new DAO_Employee();
		// Mientras no se haya cerrado la conexion con el cliente
		while(!_clientConnection.isClosed()) {
			// Descrifra el mensaje recibido por parte del cliente y dependiendo
			// del mensaje principal, realiza un método u otro
			try {
				// Obtiene el mensaje
				inputStream = _clientConnection.getInputStream();
				dataInputStream = new DataInputStream(inputStream);
				partsMessage = dataInputStream.readUTF().split(";");
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(!partsMessage.equals(null)) {
				if (partsMessage[0].equals("login")) {
					try {
						outputStream = _clientConnection.getOutputStream();
						dataOutputStream = new DataOutputStream(outputStream);
						dataOutputStream.writeBoolean(getID());
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (partsMessage[0].equals("cobro")) {
					
				} else if (partsMessage[0].equals("ver")) {
					
				} else if (partsMessage[0].equals("exit")) {
					System.exit(0);
				}
			}
		}
		try {
			dataInputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Si el mensaje principal obtiene "login", comprueba en la base de datos <br>
	 * si la id existe o no
	 * @throws IOException
	 */
	public static boolean getID() throws IOException {
		message = partsMessage[1];
		for (Employee employee : employeeDao.getIdEmployee()) {
			if(Integer.parseInt(message) == employee.get_id()) {
				return true;
			}
		}
		return false;
	}
	
	public static void charge() {
		
	}
	
	public static void see() {
		
	}
}
