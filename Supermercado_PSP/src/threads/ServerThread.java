package threads;

import java.net.Socket;

public class ServerThread extends Thread{
	
	Socket _clientConnection = null;
	
	public ServerThread (Socket clientConnection) {
		_clientConnection = clientConnection;
	}

}
