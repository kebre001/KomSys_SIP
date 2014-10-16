import java.io.IOException;
import java.net.InetAddress;

//import SipWolrd;

public class StateTrying extends SipState{
	AudioStreamUDP stream = null;
	Sip sip=null;
	public StateTrying(Sip sip){
	//	SipWorld sw = new SipWorld();
		this.sip=sip;
		//Scanner scan = new Scanner(System.in);
		this.sip.setState(this);
		
		

		if(!SipWorld.sip.printState().equalsIgnoreCase("trying")){
			SipWorld.sip.setState(this);
			System.out.println("Forcing state trying");
		}
		
		//String reply;
		//String host = null;
		try {
			// The AudioStream object will create a socket,
			// bound to a random port.
			stream = new AudioStreamUDP();
			int localPort = stream.getLocalPort();
			System.out.println("Bound to local port = " + localPort);
			SipWorld.sp.setAudioStreamUDP(stream);

			
			// Set the address and port for the callee.
			//System.out.println("What's the remote port number?");
			//reply = scan.nextLine().trim();
			//int remotePort = Integer.parseInt(reply);
			//System.out.println("Trying... Thread: "+Thread.currentThread().getId());
			//int remotePort = 5060;
			//SipWorld.sp.setPort(localPort);// kanske ar helt fel
			int remotePort = SipWorld.sp.getPort();
			//System.out.println("Port SipWorld: "+SipWorld.sp.getPort());
			//InetAddress address = InetAddress.getByName(host); //slang in argument
			InetAddress address = SipWorld.sp.getIp(); //slang in argument
			System.out.println(address + ", " + remotePort);
			//stream.connectTo(address, remotePort);
			SipWorld.sp.setUdpPort(localPort);
			//SipWorld.sp.setUdpPort(12346);
			
			//sip.setState(Sip.SipEvent.TRYRING);
	//this.sip.processNextEvent(Sip.SipEvent.TRYRING);
			
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//this.sip.processNextEvent(Sip.SipEvent.TRYSUCCESS);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("No one is calling you BYE");
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
