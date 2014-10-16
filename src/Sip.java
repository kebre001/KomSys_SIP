
public class Sip {
	public enum SipEvent { ACK, ERROR, BYE, RECEIVE, OK, BUSY, IDLE, INVITE, TRYSUCCESS, TRYRING, FIDLE};
	private SipState currentState = null;
	
    public Sip()
    {
    	currentState = new StateIdle(this, false);
    	SipWorld.sp.idle=currentState;
    }

    public void processNextEvent (SipEvent event)
    {
	switch(event)
	    {
	    case ACK: currentState = currentState.ack(); break;
	    case ERROR: currentState = currentState.error(); break;
	    case BYE: currentState = currentState.bye(); break;
	    case RECEIVE: currentState = currentState.receive(); break;
	    case OK: currentState = currentState.ok(); break;
	    case BUSY: currentState = currentState.busy(); break;
	    case IDLE: currentState = currentState.idle(); break;
	    case INVITE: currentState = currentState.invite(); break;
	    case TRYSUCCESS: currentState = currentState.trySuccess(); break;
	    case TRYRING: currentState = currentState.tryRing(); break;
	    case FIDLE: currentState = currentState.fIdle(); break;
	    }
    }

    public String printState()
    {
    	 return currentState.printState();
    }
    public void setState(SipState s){
    	this.currentState = s;
    }
}
