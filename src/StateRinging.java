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

		stream = SipWorld.sp.getAudioStreamUDP();
		
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
		out.println("OK "+ SipWorld.sp.getUdpPort());
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
			e1.printStackTrace();
		}
		
		// ####### SLUT ACK ########
		if(received.startsWith("ACK")){
			SipWorld.sp.setUdpPort(Integer.parseInt(received.substring(4).trim()));
			SipWorld.sp.setIp(tcp.getInetAddress());
			this.sip.processNextEvent(Sip.SipEvent.OK);
		}else{
			System.out.println("No ack received");
		}
	}
	public synchronized SipState ok(){
		return new StateConnected(sip);
	}
	public SipState error(){
		return new StateDisconnected(sip);
	}
	
	public String printState(){
		return "Ringing";
	}
}
