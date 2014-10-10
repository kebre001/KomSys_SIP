
public class StateDisconnected extends SipState{
	public StateDisconnected(){
		
	}
	public SipState idle(){
		return new StateIdle();
	}
	
	public String printState(){
		return "Disconnected";
	}
}
