import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class StateWaiting extends SipState {
	Sip sip =null;

	public StateWaiting(Socket tcp, Sip sip) {
		BufferedReader in = null;
		PrintWriter out = null;
		boolean ok = false;
		boolean ack = false;
		this.sip = sip;
		
		sip.setState(this);
		
		if(!SipWorld.sip.printState().equalsIgnoreCase("waiting")){
			SipWorld.sip.setState(this);
			System.out.println("Forcing state waiting 1");
		}

		while (ok == false && ack == false) {
			try {
				in = new BufferedReader(new InputStreamReader(tcp.getInputStream()));
				out = new PrintWriter(tcp.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(tcp.getInetAddress() + ":" + tcp.getPort());
			try {
				String received = in.readLine();
				if(received.startsWith("OK")){

					//System.out.println("udpPort Received: " + received.substring(3));
					System.out.println(received);
					
					SipWorld.sp.setUdpPort(Integer.parseInt(received.substring(3).trim()));
					
					//########## Audio Stream setup ###########
					
					AudioStreamUDP stream = null;
					int localUdpPort = -1;
					try {
						stream = new AudioStreamUDP();
						localUdpPort = stream.getLocalPort();
						//System.out.println("Bound to local port = " + localUdpPort);
						
						SipWorld.sp.setAudioStreamUDP(stream);
						SipWorld.sp.setIp(tcp.getInetAddress());
						SipWorld.sp.setTcp(tcp);
					}
					finally {
						
					}
					
					//########## Slut Audio Stream ############
					
					out.println("ACK " + localUdpPort);
					out.flush();
					ok = true;
					ack = true;
					break;
				}else{
					System.out.println("Recived BUSY, exiting");
					this.sip.processNextEvent(Sip.SipEvent.ERROR);
					return;
				}
			} catch (IOException e) {
				sip.processNextEvent(Sip.SipEvent.ERROR);
				e.printStackTrace();
			}
		}
		sip.processNextEvent(Sip.SipEvent.ACK);
	}

	public synchronized SipState ack() {
		return new StateConnected(sip);
	}

	public SipState error() {
		return new StateDisconnected(sip);
	}

	public String printState() {
		return "Waiting";
	}
}
