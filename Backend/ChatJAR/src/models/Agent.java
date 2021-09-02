package models;

public abstract class Agent implements agents.Agent {

	private static final long serialVersionUID = 1L;

	protected AID aid;
	
	@Override
	public void init(AID aid) {
		this.aid = aid;
	}
	
	@Override
	public AID getAID() {
		return aid;
	}
	
}
