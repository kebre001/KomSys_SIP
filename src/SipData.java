import java.net.InetAddress;
import java.net.Socket;


public class SipData {
	private InetAddress ip;
	private int port;
	public Socket incoming;
	public boolean goIdle;
	public SipState idle;
	public String scanned;
	
	private int portUdp;
	private AudioStreamUDP asu;
	public boolean answer=false;
	
	public SipData(InetAddress inetAddress, int port){
		this.ip=inetAddress; this.port=port;
		
	}
	public synchronized void setIp(InetAddress newIp){
		this.ip=newIp;
	}

	public synchronized InetAddress getIp(){
		return this.ip;
	}
	
	public synchronized void setPort(int newPort){
		this.port=newPort;
	}

	public synchronized int getPort(){
		return this.port;
	}
	
	public synchronized void setTcp(Socket tcp){
		this.incoming = tcp;
	}
	public synchronized Socket getTcp(){
		return incoming;
	}
	public synchronized void setUdpPort(int port){
		this.portUdp = port;
	}
	public synchronized int getUdpPort(){
		return portUdp;
	}
	public synchronized void setAudioStreamUDP(AudioStreamUDP asu){
		this.asu = asu;
	}
	public synchronized AudioStreamUDP getAudioStreamUDP(){
		return this.asu;
	}
	public synchronized void nullEverything(){
		  ip =null;
		  port=-1;
		 incoming=null;
		 portUdp=-1;
		 asu=null;
		answer=false;
		
	}
}
