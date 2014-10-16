public abstract class SipState {
	
	public SipState invite() {
		return this;
	}
	public SipState ack() {
		return this;
	}
	public SipState error() {
		return this;
	}
	public SipState bye() {
		return this;
	}
	public SipState ok() {
		return this;
	}
	public SipState busy() {
		return this;
	}
	public SipState receive(){
		return this;
	}
	public SipState idle(){
		return this;
	}
	public SipState trySuccess(){
		return this;
	}
	public SipState tryRing() {
		return this;
	}
	public String printState() {
		return null;
	}
	public SipState fIdle() {
		return this;
	}
}