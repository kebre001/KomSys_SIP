
public class StateIdle extends SipState{
	
	public StateIdle(){
		
	}
	public SipState invite(){
		return new StateWaiting();
	}
	public SipState receive(){
		return new StateTrying();
	}
	@Override
	public String printState(){
		return "Idle";
	}

}
