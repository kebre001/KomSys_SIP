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
		/// if buysy abort and answer busy
		if(!SipWorld.sip.printState().equalsIgnoreCase("idle")){
			System.out.println("Sending BUSY");
			out.println("BUSY");
			out.flush();
			System.out.println("Sending BUSY");
			sip.processNextEvent(Sip.SipEvent.ERROR);
		}else{
			SipWorld.sp.setIp(incoming.getInetAddress());
			SipWorld.sp.setPort(incoming.getPort());
			
			System.out.println("Setting addresses: "+sip.printState());
			SipWorld.sp.setTcp(incoming);
			//SipWorld.sp.allowInvite=true;
			sip.processNextEvent(Sip.SipEvent.RECEIVE);
			//Denna kod sker nar mottagen kod har slutforts
			sip.setState(new StateIdle(sip, false));		
		}
		
		
	}
}

public class SipWorld extends Thread {
	public static SipData sp;
	//static SipHandler SH;
	static Sip sip;
	static runClient client;
	static Scanner scan;
	public static Thread clientT;
	
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
		
			try {
				if(serverT!=null)
				serverT.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		serverT.start();
		
		// ############## KLIENT ###################


		scan = new Scanner(System.in);
		while(true){
		int temp;
		do{
		//System.out.println(">>> " + sip.printState() + " <<<");
		System.out.println("1. Send invite");
		System.out.println("2. Answer");
		System.out.println("3. State");
		System.out.println("4. Bye");

		temp = scan.nextInt();

		switch(temp){

			case 1: 
				//sp.nullEverything();
			try {
				
				if(clientT!=null)
				clientT.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				client =null;
				client = new runClient(sip);
				clientT = new Thread(client);

				System.out.println("Enter ip and Port:");
				scan.nextLine();
				String newData = scan.nextLine();
				sp.scanned=newData;
				clientT.start();

				break;
			case 2: if(sip.printState().equalsIgnoreCase("trying")){sp.answer=true;}else{ System.out.println("No one is calling, come back later"); break;} 
			case 3: System.out.println(sip.printState()); System.out.println("Statecheck... Thread: "+Thread.currentThread().getId());break;
			case 4: sip.processNextEvent(Sip.SipEvent.BYE);break;
		}
		}while(!(temp == 0));
		scan.close();
		

		}
		// ############## SLUT KLIENT ##############
		
		
		//sip = new Sip(); <---- FLYTTAD 
		
		//System.out.println(" error koll 2 !!!!");
		//System.out.println("innan error 1,2 "+sip.printState());
		//StateIdle si = new StateIdle(sip);

	}
}


