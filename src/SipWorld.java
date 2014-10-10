import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class SipWorld extends Thread{
	
	public static void main(String[] args)
    {
	Scanner scan = null;
	
	//Setup TCP listener 
	Socket peerConnectionSocket=null;
	PrintWriter out = null;
	try {
		ServerSocket ss = new ServerSocket(5060);
		System.out.println("Waiting for connection...");
		peerConnectionSocket = ss.accept();
		System.out.println("Connected from:" + peerConnectionSocket.getRemoteSocketAddress() + ":" + peerConnectionSocket.getPort());
		scan = new Scanner (peerConnectionSocket.getInputStream());
		out = new PrintWriter(new OutputStreamWriter(peerConnectionSocket.getOutputStream()));
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
	Sip sip = new Sip(); 
	int choice = 0;
	do
	    {
		//out.print is sendig it to connector
		out.println("SIP P2P Client");
		out.println("1. Send Ack.");
		out.println("2. Make Error");
		out.println("3. Send Bye");
		out.println("4. Send Receive");
		out.println("5. Send OK?");
		out.println("6. Send Busy");
		out.println("7. Make Idle");
		out.println("8. Invite");
		out.println("9. Current state");
		out.println("0. I wanna go home.");
		out.print(">");
		out.flush();
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
		    case 9: out.println("\n>>> " + sip.printState() + " <<<"); break;
		    }
		out.println("");
	    }
	while(choice != 0);
    }
}
