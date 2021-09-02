package messagemanager;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import agentmanager.AgentManagerRemote;
import agents.Agent;
import models.AID;
import ws.Logger;

/**
 * Message-Driven Bean implementation class for: MDBConsumer
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/chat-queue") })

public class MDBConsumer implements MessageListener {

	@EJB
	AgentManagerRemote agentManager;
	@EJB Logger logger;

	public void onMessage(Message message) {
		try {
			ACLMessage agentMessage = (ACLMessage) ((ObjectMessage) message).getObject();
			for (AID aid : agentMessage.getReceivers()) {
				
				Set<AID> receivers = new HashSet<AID>();
				receivers.add(aid);
				agentMessage.setReceivers(receivers);
				
				Agent agent = agentManager.getRunningAgentByAID(aid);
				if (agent != null) {
					log(agentMessage);
					agent.handleMessage(agentMessage);
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	private void log(ACLMessage message) {
		logger.send(logger.ACLToString(message));
	}

}
