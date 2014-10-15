import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;


class ClientHandler extends Thread {
	protected Socket incoming;
	protected BufferedReader in;
	protected PrintWriter out;
	protected List<ClientHandler> activeThreads;
	protected Scanner scan = null;
	public Sip sip;
	
	

	public ClientHandler(Socket incoming, List<ClientHandler> activeThreads, Sip sip) {
		this.incoming = incoming;
		this.activeThreads = activeThreads;
		this.sip=sip;
		//SipData data = new SipData();
		
		try {
			if (incoming != null) {
				in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
				out = new PrintWriter(new OutputStreamWriter(incoming.getOutputStream()));
			}
		} catch (Exception e) {
			System.out.println("Error1: " + e);
		}
		
		//System.out.println("Sent OK");
		//out.println("OK");
		//out.flush();
		
		System.out.println("Incoming Inet Adress: "+ incoming.getInetAddress());
		System.out.println("Incoming get Port: "+ incoming.getPort());
		
		SipWorld.sp.setIp(incoming.getInetAddress());
		SipWorld.sp.setPort(incoming.getPort());
		
		System.out.println("Recove errror: "+sip.printState());
		SipWorld.sp.setTcp(incoming);
		
		sip.processNextEvent(Sip.SipEvent.RECEIVE);
		
		//Denna kod sker nar mottagen kod har slutforts
		
		sip.setState(new StateIdle(sip, false));
		
	}


}

public class SipWorld extends Thread {
	public static SipData sp;
	//static SipHandler SH;
	static Sip sip;
	static runClient client;
	

	public static void main(String[] args) {
		//System.out.println(" error koll 1 !!!!");
		//SH = new SipHandler();
		
		sp = new SipData(null, 0);
		sip = new Sip(); 
		//int temp = -1;
		//System.out.println(" error koll 1,2 !!!!");
		
		//System.out.println(" error koll 1,3 !!!!");
		SipWorld_Server sws = new SipWorld_Server(sip);
		Thread serverT = new Thread(sws);
		//SH.sips.add(sip);
		
		serverT.start();
		
		// ############## KLIENT ###################
		client = new runClient(sip);
		client.run();
		// ############## SLUT KLIENT ##############
		
		
		//sip = new Sip(); <---- FLYTTAD 
		
		//System.out.println(" error koll 2 !!!!");
		//System.out.println("innan error 1,2 "+sip.printState());
		//StateIdle si = new StateIdle(sip);

	}
}


