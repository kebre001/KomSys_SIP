
public class StateDisconnected extends SipState{
	Sip sip=null;
	public StateDisconnected(Sip sip){
		this.sip=sip;
		sip.setState(this);
		System.out.println("Kor idle");
		this.sip.processNextEvent(Sip.SipEvent.IDLE);
	}
	public SipState idle(){
		return new StateIdle(sip, true);
	}
	
	public String printState(){
		return "Disconnected";
	}
}
