
public class StateWaiting extends SipState{
	public StateWaiting(){
		
	}
	
	public SipState ack(){
		return new StateConnected();
	}
	public SipState error(){
		return new StateDisconnected();
	}
	public String printState(){
		return "Waiting";
	}
}
