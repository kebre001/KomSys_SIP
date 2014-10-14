import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class StateIdle extends SipState{
	Scanner scan = new Scanner(System.in);
	SipData sd = null;
	Sip sip = null;
	StateWaiting sw = null;
	runClient client;
	
	public StateIdle(Sip sip, boolean runClient){
		//scan = new Scanner(System.in);
		System.out.println("[IDLE] Rad 16");
		this.sip=null;
		this.sip= sip;
		//run client
		
		//run client
		System.out.println("[IDLE] Rad 18");
		this.sip.setState(this);
		this.sip=sip;
		this.sip.setState(this);
		System.out.println("[IDLE] Rad 20");
		//this.sip=new Sip();
		
		if(runClient){
			client =null;
			client = new runClient(sip);
			client.run();
		}
		//init();		
		
		//sip.processNextEvent(Sip.SipEvent.INIT);
	}
	
	public SipState invite(){
		Socket peerSocket = null; 
		
		System.out.println("Idle... Thread: "+Thread.currentThread().getId());
		
		System.out.println("Connecting to client.");
		System.out.println("Enter ip and Port:");
		String newData = scan.nextLine();
		//newData = scan.nextLine();
		
		
		String[] newDataArray = newData.split(" ");
		System.out.println("Start calling..");
		try {
			peerSocket = new Socket(newDataArray[0], Integer.parseInt(newDataArray[1]));
			System.out.println("Waiting for answer");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			sd = new SipData(InetAddress.getByName(newDataArray[0]), Integer.parseInt(newDataArray[1]));
		} catch (NumberFormatException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return new StateWaiting(peerSocket, sip);
	}
	public SipState receive(){
		//System.out.println("[IDLE] Rad 68");
		return new StateTrying(sip);
	}
	
	public String printState(){
		return "Idle";
	}

}
