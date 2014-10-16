import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


class ClientHandler implements Runnable {
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
	}

	@Override
	public void run(){
		try {
			if (incoming != null) {
				in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
				out = new PrintWriter(new OutputStreamWriter(incoming.getOutputStream()));
			}
		} catch (Exception e) {
			System.out.println("Unable to start socket thread \n System will exit");
			System.exit(0);
		}
		
		SipWorld.sp.setIp(incoming.getInetAddress());
		SipWorld.sp.setPort(incoming.getPort());
		SipWorld.sp.setTcp(incoming);
		
		sip.processNextEvent(Sip.SipEvent.RECEIVE);
		sip.setState(new StateIdle(sip, false));		
	}
}

public class SipWorld extends Thread {
	public static SipData sp;
	static Sip sip;
	static runClient client;
	static Scanner scan;
	public static Thread clientT;
	
	public static void main(String[] args) {
		sp = new SipData(null, 0);
		sip = new Sip(); 

		SipWorld_Server sws = new SipWorld_Server(sip);
		Thread serverT = new Thread(sws);
		
		serverT.start();
		
		// ############## KLIENT ###################
		
		
		
		runMenu();
		
		
		// ############## SLUT KLIENT ##############
	}
	
	
	public static void runMenu(){
		scan = new Scanner(System.in);
		while(true){
			int temp = 0;
			do{
			System.out.println("1. Send invite");
			System.out.println("2. Answer");
			System.out.println("3. State");
			System.out.println("4. Bye");
			
			try{
			temp = scan.nextInt();
			}catch(InputMismatchException e){
				System.out.println("Input error, expection 0-9");
				runMenu();
			}

			switch(temp){

				case 1: 
					try {
					if(clientT!=null)
					clientT.join();
					} catch (InterruptedException e) {
						System.out.println("Unable to invite, try again");
						break;
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
	}
}