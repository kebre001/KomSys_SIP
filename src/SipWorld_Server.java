import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class SipWorld_Server implements Runnable {
	List<ClientHandler> activeThreads;
	//private SIPHandler sipHandler = null;
	public int serverPort = 5062;
	private ServerSocket serverSocket = null;
	private Socket peerSocket;
	private Sip newState;
	private BufferedReader in;
	private PrintWriter out;
	private Thread clientHandler;

	public SipWorld_Server(Sip stateHandler) {
		this.newState=stateHandler;
	}

	
	@Override
	public void run() {
		// Setup TCP listener

		activeThreads = Collections.synchronizedList(new ArrayList<ClientHandler>());

		
		
		try {
			serverSocket = new ServerSocket(serverPort);
			
			System.out.println("[SERVER]Waiting for connection on: " + serverSocket.getLocalPort());
			
			for (;;) {
				//System.out.println("--");
				peerSocket = serverSocket.accept();
				try {
					in = new BufferedReader(new InputStreamReader(peerSocket.getInputStream()));
					out = new PrintWriter(new OutputStreamWriter(peerSocket.getOutputStream()));
				} catch (Exception e) {
					System.out.println("Error1: " + e);
				}
				System.out.println("[SERVER]Connected from:"+ serverSocket.toString());
				if(!SipWorld.sip.printState().equalsIgnoreCase("idle")){
					
					System.out.println("Sending BUSY");
					out.println("BUSY");
					out.flush();
				}else{
					System.out.println("new ClientHandler!!");
					ClientHandler newThread = new ClientHandler(peerSocket, activeThreads, newState);
					clientHandler = new Thread(newThread);
					System.out.println("Kommer ut ur clientHandler!!!");
					// sp = new
					// SipData(serversocket.getInetAddress(),serversocket.getPort());
					clientHandler.start();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
