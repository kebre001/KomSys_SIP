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
	
	public StateConnected(Sip sip){
		boolean bye = false;
		this.sip=sip;
		sip.setState(this);
		this.sip=sip;
		sip.setState(this);
		//System.out.println("Connected... Thread: "+Thread.currentThread().getId());
		//System.out.println("Connected !!!!  :"+sip.printState());
		//System.out.println("[CONNECTED] setThis");
		
		AudioStreamUDP audio = SipWorld.sp.getAudioStreamUDP();
	//	int audioPort = SipWorld.sp.getUdpPort();
		
		Socket tcp = SipWorld.sp.getTcp();
		//System.out.println("TCP Connection: "+tcp.getInetAddress());
		try {
			
				in = new BufferedReader(new InputStreamReader(tcp.getInputStream()));
				out = new PrintWriter(new OutputStreamWriter(tcp.getOutputStream()));
			
		} catch (Exception e) {
			System.out.println("Error1: " + e);
		}
		System.out.println("TCP Connection: "+tcp.getInetAddress()+ " Port: "+ tcp.getPort());
		// ######### STARTA CONNECTION
		System.out.println("AudioStream: " + SipWorld.sp.getIp() + ":" + SipWorld.sp.getUdpPort());
		try {
			audio.connectTo(SipWorld.sp.getIp(), SipWorld.sp.getUdpPort());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		audio.startStreaming();
		
		// ######## SLUT CONNECTION
		String cmd = null; 
		// KEEP ALIVE
		/*
		try {
			tcp.setSoTimeout(2000);
		} catch (SocketException e1) {
			System.out.println("Connection is dead");
			e1.printStackTrace();
			bye=true;
		}*/
		
		while(!bye){
			out.println("alive");
			out.flush();
			
			//System.out.println("Connection is alive");
			try {
				cmd = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(cmd == null){
				System.out.println("Connection is dead");
				//bye = true;
			}else{System.out.println("Connection is alive");
			System.out.println("inline received: "+cmd);
			
			}
		}
		this.sip.processNextEvent(Sip.SipEvent.BYE);
		//this.sip.processNextEvent(Sip.SipEvent.OK);
	}
	
	public SipState bye(){
		System.out.println("[CONNECTED] Haning up");
		return new StateDisconnected(sip);
	}
	
	public String printState(){
		return "Connected";
	}
}
