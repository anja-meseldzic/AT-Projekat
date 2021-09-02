package agents;

import java.io.Serializable;

import javax.ejb.Remote;
import javax.jms.Message;

import messagemanager.AgentMessage;
import models.AID;

@Remote
public interface Agent extends Serializable {

	public void handleMessage(AgentMessage message);
	
	public void init(AID aid);
	
	public AID getAID();
}
