import java.io.IOException;
import java.net.InetAddress;

//import SipWolrd;

public class StateTrying extends SipState{
	AudioStreamUDP stream = null;
	Sip sip=null;
	public StateTrying(Sip sip){
		this.sip=sip;
		this.sip.setState(this);
		
		if(!SipWorld.sip.printState().equalsIgnoreCase("trying")){
			SipWorld.sip.setState(this);
			System.out.println("Forcing state trying");
		}
		
		try {
			stream = new AudioStreamUDP();
			int localPort = stream.getLocalPort();
			System.out.println("Bound to local port = " + localPort);
			SipWorld.sp.setAudioStreamUDP(stream);

			int remotePort = SipWorld.sp.getPort();
			InetAddress address = SipWorld.sp.getIp(); //slang in argument
			System.out.println(address + ", " + remotePort);
			SipWorld.sp.setUdpPort(localPort);
			
			while(true){
				if(SipWorld.sp.answer){
					this.sip.processNextEvent(Sip.SipEvent.TRYRING);
					break;
				}
				if(!SipWorld.sip.printState().equalsIgnoreCase("trying")){
					System.out.println("Im not in trying!");
					System.out.println("Setting myself to trying");
					SipWorld.sip.setState(this);
				}
				try {
					Thread.sleep(1500);
					System.out.println("Ring ring! Ring ring!");
					System.out.println("Thread ID: "+Thread.currentThread().getId());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.out.println("No one is calling you");
			e.printStackTrace();
		}
		finally {
			if(stream != null) stream.close();
		}
	}
	public SipState tryRing(){
		return new StateRinging(sip);
	}
	public SipState error(){
		return new StateDisconnected(sip);
	}
	public String printState(){
		return "Trying";
	}

}
