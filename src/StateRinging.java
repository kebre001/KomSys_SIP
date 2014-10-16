import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class StateRinging extends SipState{
	protected BufferedReader in;
	protected PrintWriter out;
	Sip sip=null;
	public AudioStreamUDP stream;
	public StateRinging(Sip sip){
		this.sip=sip;
		sip.setState(this);
		

		if(!SipWorld.sip.printState().equalsIgnoreCase("ringing")){
			SipWorld.sip.setState(this);
			System.out.println("Forcing state ringing");
		}
		
		
		//System.out.println(stream);
		//System.out.println("Ringing... Thread: "+Thread.currentThread().getId());
		stream = SipWorld.sp.getAudioStreamUDP();
		
		//System.out.println("Ring ring ring, Port: "+stream.getLocalPort());
		Socket tcp = SipWorld.sp.getTcp();
		
		try {
			if (tcp != null) {
				in = new BufferedReader(new InputStreamReader(tcp.getInputStream()));
				out = new PrintWriter(new OutputStreamWriter(tcp.getOutputStream()));
			}
		} catch (Exception e) {
			System.out.println("Error1: " + e);
		}
		
		
		try{
		out.println("OK "+ SipWorld.sp.getUdpPort());//This udp port is not suppose to be 0
		out.flush();
		}catch(NullPointerException e){
			System.out.println("No one is calling, sorri");
			System.out.println("Returning to IDLE");
			this.sip.processNextEvent(Sip.SipEvent.ERROR);
		}
		
		SipWorld.sp.setAudioStreamUDP(stream);
		
		// ###### TA EMOT ACK ######
		
		String received = null;
		try {
			received = in.readLine().trim();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// ####### SLUT ACK ########
		if(received.startsWith("ACK")){
			//System.out.println("Received ACK");
			//System.out.println("RemoteUdpPort: " + Integer.parseInt(received.substring(4)));
			SipWorld.sp.setUdpPort(Integer.parseInt(received.substring(4).trim()));
			//SipWorld.sp.setUdpPort(1337);
			SipWorld.sp.setIp(tcp.getInetAddress());
			this.sip.processNextEvent(Sip.SipEvent.OK);
		}else{
			System.out.println("No ack received");
			//this.sip.processNextEvent(Sip.SipEvent.ERROR);
		}
	}
	public SipState ok(){
		return new StateConnected(sip);
	}
	public SipState error(){
		return new StateDisconnected(sip);
	}
	
	public String printState(){
		return "Ringing";
	}
}
