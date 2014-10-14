import java.util.Scanner;

public class runClient{
	Sip sip=null;
	Scanner scan = new Scanner(System.in);
	public runClient(Sip sip){
		
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
		System.out.println("Starting Client");
		
		int temp;
		do{
		//	System.out.println(">>> " + sip.printState() + " <<<");
			System.out.println("1. Send invite");
			System.out.println("2. Answer");
			System.out.println("3. State");
			System.out.println("4. Bye");

		temp = scan.nextInt();
		
		switch(temp){
		
			case 1: sip.processNextEvent(Sip.SipEvent.INVITE); break;
			case 2: sip.processNextEvent(Sip.SipEvent.RECEIVE); break;
			case 3: System.out.println(sip.printState()); System.out.println("Statecheck... Thread: "+Thread.currentThread().getId());break;
			case 4: sip.processNextEvent(Sip.SipEvent.BYE);break;
		}
		}while(!(temp == 0));
		scan.close();
	}
	
	public void clear(){
		this.sip=null;
		//empty all other flags that might cause auto-trying
		
		
	}
	
}