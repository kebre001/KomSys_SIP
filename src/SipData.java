import java.net.InetAddress;
import java.net.Socket;


public class SipData {
	private InetAddress ip;
	private int port;
	public Socket incoming;
	public boolean goIdle;
	
	private int portUdp;
	private AudioStreamUDP asu;
	
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
		System.out.println("From Data, getUdpPort: "+this.portUdp);
		return portUdp;
	}
	public void setAudioStreamUDP(AudioStreamUDP asu){
		System.out.println("From Data, setAudioStreamUDP: "+asu);
		this.asu = asu;
	}
	public AudioStreamUDP getAudioStreamUDP(){
		System.out.println("From Data, getAudioStreamUDP: "+this.asu);
		return this.asu;
	}
}
