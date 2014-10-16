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
		if(SipWorld.sp.goIdle){
			this.sip=null;
			this.sip= sip;

			runClient=true;
		}else{
			this.sip=null;
			this.sip= sip;
		}
		sip.setState(this);		
	}
	
	
	public SipState invite(){
		
		Socket peerSocket = null; 
		String newData = SipWorld.sp.scanned;		
		String[] newDataArray = newData.split(" ");
		
		System.out.println("Start calling..");
		try {
			peerSocket = new Socket(newDataArray[0], Integer.parseInt(newDataArray[1]));
			System.out.println("Waiting for answer");
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("MISSING ARGUMENTS");
			System.out.println("Usage: <host> <port> e.x localhost 3490");
			System.out.println("System will exit");
			System.exit(0);
		}catch (IOException e1) {
			System.out.println("Invalid hostname or port");
			System.exit(0);
		}
		
		try {
			sd = new SipData(InetAddress.getByName(newDataArray[0]), Integer.parseInt(newDataArray[1]));
		} catch (NumberFormatException | UnknownHostException e) {
			e.printStackTrace();
		}
		return new StateWaiting(peerSocket, sip);
	}
	
	public SipState receive(){
		return new StateTrying(sip);
	}
	
	public SipState fIdle(){
		return this;
	}
	
	public String printState(){
		return "Idle";
	}

}
