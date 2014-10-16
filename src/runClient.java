import java.util.Scanner;

public class runClient implements Runnable{
	Sip sip=null;
	Scanner scan = new Scanner(System.in);
	public runClient(Sip sip){
		System.out.println("'runClient constructor'");
		this.sip=null;
		this.sip=sip;
	}
	
	public void run(){
		//clear();
		System.out.println("'run'");
		
		sip.processNextEvent(Sip.SipEvent.INVITE);
		
		//This is the point we return from a connection
		
		sip.setState(SipWorld.sp.idle);
		
	}
	
	public void clear(){
		this.sip=null;
		//empty all other flags that might cause auto-trying
		
		
	}
	
}