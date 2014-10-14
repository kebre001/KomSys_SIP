
public class StateConnected extends SipState{
	Sip sip = null;
	
	public StateConnected(Sip sip){
		boolean bye = true;
		this.sip=sip;
		sip.setState(this);
		this.sip=sip;
		sip.setState(this);
		System.out.println("Connected... Thread: "+Thread.currentThread().getId());
		System.out.println("Connected !!!!  :"+sip.printState());
		System.out.println("[CONNECTED] setThis");
		while(!bye){
			
			
			
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//this.sip.processNextEvent(Sip.SipEvent.OK);
	}
	
	public SipState bye(){
		System.out.println("[CONNECTED] Haning up");
		return new StateDisconnected(sip);
	}
	
	public String printState(){
		return "Connected";
	}
}
