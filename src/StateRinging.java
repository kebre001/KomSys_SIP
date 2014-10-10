
public class StateRinging extends SipState{
	public StateRinging(){
		
	}
	
	public SipState ok(){
		return new StateConnected();
	}
	
	public String printState(){
		return "Ringing";
	}
}
