package agents;

import java.io.Serializable;

import javax.ejb.Remote;
import javax.jms.Message;

import messagemanager.ACLMessage;
import models.AID;

@Remote
public interface Agent extends Serializable {

	public void handleMessage(ACLMessage message);
	
	public void init(AID aid);
	
	public AID getAID();
}
