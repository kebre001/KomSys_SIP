
public class Sip {
	public enum SipEvent { ACK, ERROR, BYE, RECEIVE, OK, BUSY, IDLE, INVITE};

    public Sip()
    {
	currentState = new StateIdle();
    }

    public void processNextEvent (SipEvent event)
    {
	switch(event)
	    {
	    case ACK: currentState = currentState.idle(); break;
	    case ERROR: currentState = currentState.invite(); break;
	    case BYE: currentState = currentState.receive(); break;
	    case RECEIVE: currentState = currentState.receive(); break;
	    case OK: currentState = currentState.ok();
	    case BUSY: currentState = currentState.busy();
	    case IDLE: currentState = currentState.idle();
	    case INVITE: currentState = currentState.invite();
	    }
    }

    private SipState currentState;

    public void printState()
    {
	currentState.printState();
    }
}
