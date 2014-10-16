public class StateDisconnected extends SipState {
	Sip sip = null;

	public StateDisconnected(Sip sip) {
		this.sip = sip;
		sip.setState(this);
		SipWorld.sp.goIdle = true;

		if (!SipWorld.sip.printState().equalsIgnoreCase("disconnected")) {
			SipWorld.sip.setState(this);
			System.out.println("Forcing state disconnected 9");
		}

		this.sip.processNextEvent(Sip.SipEvent.IDLE);
	}

	public synchronized SipState idle() {
		System.out.println("Running idle");
		SipWorld.sp.answer = false;

		return new StateIdle(new Sip(), false);
	}

	public String printState() {
		return "Disconnected";
	}
}
