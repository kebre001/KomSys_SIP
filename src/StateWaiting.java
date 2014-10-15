import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
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

		//System.out.println("Waiting... Thread: "+Thread.currentThread().getId());
		while (ok == false && ack == false) {
			//System.out.println("Wait loop");
			try {
				in = new BufferedReader(new InputStreamReader(tcp.getInputStream()));
				out = new PrintWriter(tcp.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			/*try {
				System.out.println("inline read port: "+in.readLine().substring(2));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			System.out.println(tcp.getInetAddress() + ":" + tcp.getPort());
			try {
				String received = in.readLine();
				if(received.startsWith("OK")){
					//in.readLine().substring(2);
					//System.out.println("[WAITING] Sending ACK");

					System.out.println("udpPort Received: " + received.substring(3));
					System.out.println(received);
					// Puts the remote UDP port in SipData
					SipWorld.sp.setUdpPort(Integer.parseInt(received.substring(3)));
					
					//	int remoteUdpPort = Integer.parseInt(received.substring(3));
					//########## Audio Stream setup ###########
					AudioStreamUDP stream = null;
					int localUdpPort = -1;
					try {
						// The AudioStream object will create a socket,
						// bound to a random port.
						stream = new AudioStreamUDP();
						localUdpPort = stream.getLocalPort();
						System.out.println("Bound to local port = " + localUdpPort);

						//stream.connectTo(tcp.getInetAddress(), remoteUdpPort);
						
						SipWorld.sp.setAudioStreamUDP(stream);
						SipWorld.sp.setIp(tcp.getInetAddress());
						SipWorld.sp.setTcp(tcp);
					}
					//########## Slut Audio Stream ############
					
					// SKICKAR ACK
					out.println("ACK " + localUdpPort);
					out.flush();
					ok = true;
					ack = true;
					break;
				}
			} catch (IOException e) {
				sip.processNextEvent(Sip.SipEvent.ERROR);
				e.printStackTrace();
			}
			
		}
		sip.processNextEvent(Sip.SipEvent.ACK);

	}

	public SipState ack() {
		return new StateConnected(sip);
	}

	public SipState error() {
		return new StateDisconnected(sip);
	}

	public String printState() {
		return "Waiting";
	}
}
