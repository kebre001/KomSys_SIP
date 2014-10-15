import java.util.Scanner;

public class runClient implements Runnable{
	Sip sip=null;
	Scanner scan = new Scanner(System.in);
	public runClient(Sip sip){
		System.out.println("'runClient constructor'");
		this.sip=null;
		this.sip=sip;
		/*
		boolean tmp=true;
		if(tmp){
			run();
			tmp=false;
		}
		*/
		
	}
	
	public void run(){
		//clear();
		System.out.println("'run'");
		
		sip.processNextEvent(Sip.SipEvent.INVITE);
		
	}
	
	public void clear(){
		this.sip=null;
		//empty all other flags that might cause auto-trying
		
		
	}
	
}