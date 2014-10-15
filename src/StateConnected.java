import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;


public class StateConnected extends SipState{
	Sip sip = null;
	public BufferedReader in;
	public PrintWriter out;
	AudioStreamUDP audio;
	
	public StateConnected(Sip sip){
		boolean bye = false;
		this.sip=sip;
		sip.setState(this);
		this.sip=sip;
		sip.setState(this);
		
		audio = SipWorld.sp.getAudioStreamUDP();
		
		Socket tcp = SipWorld.sp.getTcp();
		
		try {
			in = new BufferedReader(new InputStreamReader(tcp.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(tcp.getOutputStream()));
		}catch (Exception e){
			System.out.println("Error1: " + e);
		}
		//System.out.println("TCP Connection: "+tcp.getInetAddress()+ " Port: "+ tcp.getPort());
		// ######### STARTA CONNECTION
		
		System.out.println("AudioStream: " + SipWorld.sp.getIp() + ":" + SipWorld.sp.getUdpPort());
		try {
			audio.connectTo(SipWorld.sp.getIp(), SipWorld.sp.getUdpPort());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		//audio.startStreaming();
		audio.startStreaming();
		
		// ######## SLUT CONNECTION
		String cmd = null; 
		// KEEP ALIVE
		
		try {
			tcp.setSoTimeout(1000);
		} catch (SocketException e1) {
			System.out.println("Connection is dead, bye");
			e1.printStackTrace();
			bye=true;
		}
		
		while(!bye){
			
			out.println("alive");
			out.flush();
			
			try {
				cmd = in.readLine();
			} catch (IOException e) {
				System.out.println("Line could not be read, exiting");
				System.out.println("Connection is dead");
				bye=true;
			}
			if(!SipWorld.sip.printState().equalsIgnoreCase("connected")){
				bye = true;
				System.out.println("Connection is dead");
			} 
			if(cmd == null){
				bye = true;
				System.out.println("Connection is dead");
			} 
			else{//System.out.println("Connection is alive");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		this.sip.processNextEvent(Sip.SipEvent.BYE);
		
	}
	
	public SipState bye(){
		return new StateDisconnected(sip);
	}
	
	public String printState(){
		return "Connected";
	}
}
