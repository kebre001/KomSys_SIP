
public class StateRinging extends SipState{
	public StateRinging(){
		
	}
	
	public SipState ok(){
		return new StateConnected();
	}
	public SipState error(){
		return new StateDisconnected();
	}
	
	public String printState(){
		return "Ringing";
	}
}
