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
		activeThreads = Collections.synchronizedList(new ArrayList<ClientHandler>());
		try {
			serverSocket = new ServerSocket(serverPort);
			//System.out.println("[SERVER]Waiting for connection on: " + serverSocket.getLocalPort());
			for (;;) {
				peerSocket = serverSocket.accept();
				try {
					in = new BufferedReader(new InputStreamReader(peerSocket.getInputStream()));
					out = new PrintWriter(new OutputStreamWriter(peerSocket.getOutputStream()));
				} catch (Exception e) {
					System.out.println("Unable to get stream");
					System.out.println("System will exit");
					System.exit(0);
				}
				//System.out.println("[SERVER]Connected from:"+ serverSocket.toString());
				if(!SipWorld.sip.printState().equalsIgnoreCase("idle")){
					out.println("BUSY");
					out.flush();
				}else{
					ClientHandler newThread = new ClientHandler(peerSocket, activeThreads, newState);
					clientHandler = new Thread(newThread);
					clientHandler.start();
				}
			}
		} catch (Exception e) {
			System.out.println("Unable to create listening port: " + serverPort);
			System.out.println("System will exit");
			System.exit(0);
		}
	}
}
