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
	public boolean bye=false;
	
	public StateConnected(Sip sip){
		bye = false;
		this.sip=sip;
		sip.setState(this);
		this.sip=sip;
		sip.setState(this);
		
		audio = SipWorld.sp.getAudioStreamUDP();
		
		Socket tcp = SipWorld.sp.getTcp();
		in=null;
		out=null;
		try {
			in = new BufferedReader(new InputStreamReader(tcp.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(tcp.getOutputStream()));
		}catch (Exception e){
			System.out.println("Error1: " + e);
		}

		// ######### STARTA CONNECTION
		
		try {
			audio.connectTo(SipWorld.sp.getIp(), SipWorld.sp.getUdpPort());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		audio.startStreaming();
		
		// ######## SLUT CONNECTION
		
		String cmd = null; 
		try {
			tcp.setSoTimeout(2000);
		} catch (SocketException e1) {
			System.out.println("Connection is dead, bye");
			e1.printStackTrace();
			bye=true;
		}
		int i=0;
		while(!bye){
			try {
				i++;
				out.println("alive"+i);
				out.flush();
				
				cmd = null;
				cmd = in.readLine();
			} catch (IOException e) {
				System.out.println("Line could not be read, exiting");
				//System.out.println("Connection is dead 1");
				bye=true;
			}
			if(!SipWorld.sip.printState().equalsIgnoreCase("connected")){
				SipWorld.sip.setState(this);
				//System.out.println("Connection is dead 2");
			} 
			if(cmd == null){
				bye = true;
				System.out.println("Connection is dead");
			} 
			else{
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		this.sip.processNextEvent(Sip.SipEvent.BYE);
	}
	
	public synchronized SipState bye(){
		this.bye=false;
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new StateDisconnected(sip);
	}
	
	public String printState(){
		return "Connected";
	}
}
