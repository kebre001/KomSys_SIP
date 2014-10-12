import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class ClientHandler extends Thread{
	  protected Socket incoming;
	  protected BufferedReader in;
	  protected PrintWriter out;
	  protected List<ClientHandler> activeThreads;
	  protected Scanner scan = null;
	  
	  public ClientHandler(Socket incoming, List<ClientHandler> activeThreads) {
		    this.incoming = incoming;
		    this.activeThreads=activeThreads;
		    try {
		      if (incoming != null) {
		        in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));          
		        out = new PrintWriter(new OutputStreamWriter(incoming.getOutputStream()));        
		        //System.out.println("Test 0,7");
		      }
		    } catch (Exception e) {
		      System.out.println("Error1: " + e);
		    }
		  }
	  
	  public void run(){
		  Sip client = new Sip();
		  Sip server = new Sip();
		  try {
			scan = new Scanner (incoming.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			int choice = 0;
			System.out.println("Before");
			String cmd = "null";
			while(!cmd.equals("exit")){
				cmd = scan.nextLine();
				System.out.println("After");
				if(cmd.equals("send invite")){
					
					client.processNextEvent(Sip.SipEvent.INVITE);
					out.println(client.printState());
					
					server.processNextEvent(Sip.SipEvent.RECEIVE);
					System.out.println(server.printState());
					
					server.processNextEvent(Sip.SipEvent.TRYSUCCESS);
					System.out.println(server.printState());
					
					for(;;){
						System.out.print("Pick up the phone[ok]: ");
						Scanner servScan = new Scanner(System.in);
						if(servScan.nextLine().equals("ok")){		//Lyfter pa luren
							server.processNextEvent(Sip.SipEvent.OK);
							System.out.println(server.printState());
							break;
						}
					}
					client.processNextEvent(Sip.SipEvent.ACK);
					System.out.println(client.printState());
					cmd = "null";
					
				}else if(cmd.equals("bye")){
					client.processNextEvent(Sip.SipEvent.BYE);
					out.println(client.printState());
					
					server.processNextEvent(Sip.SipEvent.BYE);
					System.out.println(server.printState());
					cmd = "null";
				}
			}
			/*do
			    {
				//out.print is sendig it to connector
				out.println(client.printState());
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
			out.println("bye");
			return;*/
		    }
	  
}

class UserInput implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
class SysoInput implements Runnable{
	
	BufferedReader input = null;
	public SysoInput(BufferedReader input){
		this.input=input;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}

public class SipWorld extends Thread{
	static List<ClientHandler> activeThreads;

	public static void main(String[] args)
    {
		//Setup TCP listener 
				Socket peerConnectionSocket=null;
				activeThreads = Collections.synchronizedList(new ArrayList<ClientHandler>());
				try {
					ServerSocket ss = new ServerSocket(5060);
					for(;;){
					System.out.println("Waiting for connection...");
					peerConnectionSocket = ss.accept();
					System.out.println("Connected from:" + peerConnectionSocket.toString());
					ClientHandler newThread = new ClientHandler(peerConnectionSocket, activeThreads);
					activeThreads.add(newThread);
					newThread.start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
    }
}
