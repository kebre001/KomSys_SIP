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

		System.out.println("Waiting... Thread: "+Thread.currentThread().getId());
		while (ok == false && ack == false) {
			System.out.println("Wait loop");
			try {
				in = new BufferedReader(new InputStreamReader(tcp.getInputStream()));
				out = new PrintWriter(tcp.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(in.readLine().contains("OK")){
					//in.readLine().substring(2);
					System.out.println("[WAITING] Sending ACK");
					System.out.println(in.readLine().substring(2));
					ok = true;
					out.write("ACK");
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
