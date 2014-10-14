import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class StateRinging extends SipState{
	protected BufferedReader in;
	protected PrintWriter out;
	Sip sip=null;
	public StateRinging(AudioStreamUDP stream, Sip sip){
		this.sip=sip;
		sip.setState(this);
		System.out.println(stream);
		System.out.println("Ringing... Thread: "+Thread.currentThread().getId());
		System.out.println("Ring ring ring, Port: "+stream.getLocalPort());
		Socket tcp = SipWorld.sp.getTcp();
		
		try {
			if (tcp != null) {
				in = new BufferedReader(new InputStreamReader(tcp.getInputStream()));
				out = new PrintWriter(new OutputStreamWriter(tcp.getOutputStream()));
			}
		} catch (Exception e) {
			System.out.println("Error1: " + e);
		}
		
		out.print("OK "+stream.getLocalPort());
		out.flush();
		
		this.sip.processNextEvent(Sip.SipEvent.OK);
		
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
