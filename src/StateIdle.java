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
		//System.out.println("[IDLE] Rad 16");
		
		if(SipWorld.sp.goIdle){
			System.out.println("go if");
			this.sip=null;
			this.sip= sip;
			//Sip sip2 = new Sip();
			//sip=sip2;
			
			//this.sip.setState(this);
			runClient=true;
			//SipWorld.sp.goIdle=false;
		}else{
			this.sip=null;
			this.sip= sip;
			System.out.println("did not go if");
			
		}

		
		
		
		sip.setState(this);
		//System.out.println("[IDLE] Rad 20");
		//this.sip=new Sip();
		/*
		if(runClient){
			SipWorld.sp.goIdle=false;
			client =null;
			client = new runClient(sip);
			client.run();	
		}*/
		
	}
	
	
	public SipState invite(){
		Socket peerSocket = null; 
		
		//System.out.println("Idle... Thread: "+Thread.currentThread().getId());
		
		//System.out.println("Connecting to client.");
		//System.out.println("Enter ip and Port:");
		String newData = SipWorld.sp.scanned;
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
	
	public SipState fIdle(){
		return this;
	}
	
	public String printState(){
		return "Idle";
	}

}
