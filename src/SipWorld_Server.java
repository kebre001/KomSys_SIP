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
	private Sip sip;
	private BufferedReader in;
	private PrintWriter out;
	private Thread clientHandler;

	public SipWorld_Server(Sip sip) {
		this.sip=sip;
	}
	public synchronized void checkIfBusy(){
		//System.out.println("[SERVER]Connected from:"+ serverSocket.toString());
		if(!SipWorld.sip.printState().equalsIgnoreCase("idle")){
			out.println("BUSY");
			out.flush();
		}else{
			ClientHandler newThread = new ClientHandler(peerSocket, activeThreads, sip);
			clientHandler = new Thread(newThread);
			clientHandler.start();
		}
	
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
					System.out.println("Unable to open stream");
				}
				//System.out.println("[SERVER]Connected from:"+ serverSocket.toString());
				checkIfBusy();
			}
		} catch (Exception e) {
			System.out.println("Port already in use: " + serverPort);
			System.out.println("System will exit");
			System.exit(0);
		}
	}
}
