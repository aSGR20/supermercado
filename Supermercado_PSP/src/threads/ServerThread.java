package threads;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerThread extends Thread{
	
	private static Socket _clientConnection = null;
	private static InputStream inputStream = null;
	private static DataInputStream dataInputStream;
	private static String message;
	private static String[] partsMessage;
	
	public ServerThread (Socket clientConnection) {
		_clientConnection = clientConnection;
	}
	
	public void run () {
		// Mientras no se haya cerrado la conexion con el cliente
		while(!_clientConnection.isClosed()) {
			// Descrifra el mensaje recibido por parte del cliente y dependiendo
			// del mensaje principal, realiza un método u otro
			try {
				// Obtiene el mensaje
				inputStream = _clientConnection.getInputStream();
				dataInputStream = new DataInputStream(inputStream);
				message = dataInputStream.readUTF();
				
				getID();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Si el mensaje principal obtiene "login", comprueba en la base de datos <br>
	 * si la id existe o no
	 * @throws IOException
	 */
	public static void getID() throws IOException {
		
	}
}
