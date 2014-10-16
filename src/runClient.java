import java.util.Scanner;

public class runClient implements Runnable{
	Sip sip=null;
	Scanner scan = new Scanner(System.in);
	public runClient(Sip sip){
		this.sip=null;
		this.sip=sip;
	}
	
	public void run(){
		sip.processNextEvent(Sip.SipEvent.INVITE);
		sip.setState(SipWorld.sp.idle);
	}
	
	public void clear(){
		this.sip=null;	
	}	
}