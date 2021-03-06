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
	public void setIp(InetAddress newIp){
		this.ip=newIp;
	}

	public InetAddress getIp(){
		return this.ip;
	}
	
	public void setPort(int newPort){
		this.port=newPort;
	}

	public int getPort(){
		return this.port;
	}
	
	public void setTcp(Socket tcp){
		this.incoming = tcp;
	}
	public Socket getTcp(){
		return incoming;
	}
	public void setUdpPort(int port){
		this.portUdp = port;
	}
	public int getUdpPort(){
		return portUdp;
	}
	public void setAudioStreamUDP(AudioStreamUDP asu){
		this.asu = asu;
	}
	public AudioStreamUDP getAudioStreamUDP(){
		return this.asu;
	}
	public void nullEverything(){
		  ip =null;
		  port=-1;
		 incoming=null;
		 portUdp=-1;
		 asu=null;
		answer=false;
		
	}
}
