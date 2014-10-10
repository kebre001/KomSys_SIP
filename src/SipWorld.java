import java.util.Scanner;


public class SipWorld {
	public static void main(String[] args)
    {
	Sip sip = new Sip();
	Scanner scan = new Scanner(System.in);
	int choice = 0;
	do
	    {
		System.out.println("SIP P2P Client");
		System.out.println("1. Send Ack.");
		System.out.println("2. Make Error");
		System.out.println("3. Send Bye");
		System.out.println("4. Send Receive");
		System.out.println("5. Send OK?");
		System.out.println("6. Send Busy");
		System.out.println("7. Make Idle");
		System.out.println("8. Invite");
		System.out.println("9. Current state");
		System.out.println("0. I wanna go home.");
		choice = scan.nextInt();
		switch(choice)
		    {
			case 1: sip.processNextEvent(Sip.SipEvent.ACK); break;
			case 2: sip.processNextEvent(Sip.SipEvent.ERROR); break;
			case 3: sip.processNextEvent(Sip.SipEvent.BYE); break;
			case 4: sip.processNextEvent(Sip.SipEvent.RECEIVE); break;
			case 5: sip.processNextEvent(Sip.SipEvent.OK); break;
		    case 6: sip.processNextEvent(Sip.SipEvent.BUSY); break;
		    case 7: sip.processNextEvent(Sip.SipEvent.IDLE); break;
		    case 8: sip.processNextEvent(Sip.SipEvent.INVITE); break;
		    case 9: sip.printState(); break;
		    }
		System.out.println("");
	    }
	while(choice != 0);
    }
}
