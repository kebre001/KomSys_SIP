import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class SipWorld_Server implements Runnable {
	List<ClientHandler> activeThreads;
	//private SIPHandler sipHandler = null;
	private int serverPort = 5060;
	private ServerSocket serverSocket = null;
	private Socket peerSocket;
	private DataOutputStream outToClient = null;
	private Sip newState;

	public SipWorld_Server(Sip stateHandler) {
		this.newState=stateHandler;
	}

	@Override
	public void run() {
		// Setup TCP listener

		activeThreads = Collections.synchronizedList(new ArrayList<ClientHandler>());

		try {
			serverSocket = new ServerSocket(5061);
			System.out.println("[SERVER]Waiting for connection on: " + serverSocket.getLocalPort());
			for (;;) {
				peerSocket = serverSocket.accept();
				System.out.println("[SERVER]Connected from:"+ serverSocket.toString());
				ClientHandler newThread = new ClientHandler(peerSocket, activeThreads, newState);
				
				// sp = new
				// SipData(serversocket.getInetAddress(),serversocket.getPort());
				activeThreads.add(newThread);
				newThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
