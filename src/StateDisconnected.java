
public class StateDisconnected extends SipState{
	Sip sip=null;
	public StateDisconnected(Sip sip){
		this.sip=sip;
		sip.setState(this);
		
		SipWorld.sp.getAudioStreamUDP().close();
		
		SipWorld.sp.goIdle = true;
		
		System.out.println("Dissconnecting");
		this.sip.processNextEvent(Sip.SipEvent.IDLE);
	}
	public SipState idle(){
		System.out.println("Running idle");
		return new StateIdle(new Sip(), false);
	}
	
	public String printState(){
		return "Disconnected";
	}
}
