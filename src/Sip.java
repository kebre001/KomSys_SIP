
public class Sip {
	public enum SipEvent { ACK, ERROR, BYE, RECEIVE, OK, BUSY, IDLE, INVITE, TRYSUCCESS};

    public Sip()
    {
	currentState = new StateIdle();
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
	    }
    }

    private SipState currentState;

    public String printState()
    {
    	 return currentState.printState();
    }
}
