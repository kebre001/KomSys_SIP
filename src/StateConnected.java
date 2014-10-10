
public class StateConnected extends SipState{
	public StateConnected(){
		
	}
	
	public SipState bye(){
		return new StateDisconnected();
	}
	
	public String printState(){
		return "Connected";
	}
}
