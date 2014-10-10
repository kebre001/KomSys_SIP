
public class StateWaiting extends SipState{
	public StateWaiting(){
		
	}
	
	public SipState ack(){
		return new StateConnected();
	}
	public String printState(){
		return "Waiting";
	}
}
