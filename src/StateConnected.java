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
		System.out.println("[Debug] 1 tcp adress: "+tcp.getInetAddress());
		/*
		try {
			System.out.println("[Debug] 2 tcp inStream: "+tcp.getInputStream().toString());
			System.out.println("[Debug] 3 tcp outStream: "+tcp.getOutputStream().toString());
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		*/
		
		System.out.println("[Debug] 2 tcp : "+tcp.getPort());
		System.out.println("[Debug] 3 tcp : "+tcp.getLocalPort());
		
		
		
		
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
				System.out.println(cmd);
			} catch (IOException e) {
	
				System.out.println("Line could not be read, exiting");
				System.out.println("CMD contains: "+cmd);
				System.out.println("Connection is dead 1");
				
				//###
				
				
				
				bye=true;
				
			}
			if(!SipWorld.sip.printState().equalsIgnoreCase("connected")){
				SipWorld.sip.setState(this);
				System.out.println("Connection is dead 2");
			} 
			if(cmd == null){
				bye = true;
				System.out.println("Connection is dead 3");
			} 
			else{//System.out.println("Connection is alive");
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		this.sip.processNextEvent(Sip.SipEvent.BYE);
		
	}
	
	public SipState bye(){
		this.bye=false;
		try {
			
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new StateDisconnected(sip);
	}
	
	public String printState(){
		return "Connected";
	}
}
