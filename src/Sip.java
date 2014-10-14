
public class Sip {
	public enum SipEvent { ACK, ERROR, BYE, RECEIVE, OK, BUSY, IDLE, INVITE, TRYSUCCESS, TRYRING};
	private SipState currentState = null;
	
    public Sip()
    {
    	//System.out.println("[SIP] Rad 8");
    	currentState = new StateIdle(this, false);
    	//System.out.println("[SIP] Rad 10");
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
	  //  case INIT: currentState = currentState.init(); break;
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
