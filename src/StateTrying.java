
public class StateTrying extends SipState{
	public StateTrying(){
		
	}
	
	public SipState error(){
		return new StateDisconnected();
	}
	public String printState(){
		return "Trying";
	}
}
