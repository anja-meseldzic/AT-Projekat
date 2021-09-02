package rest;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import agentmanager.AgentManagerRemote;
import messagemanager.ACLMessage;

import messagemanager.MessageManagerRemote;

import models.AID;
import models.AgentType;


@Stateless
@Path("/chat")
@Remote(ChatRest.class)
public class ChatBeanRest implements ChatRest {
	
	
	@EJB
	MessageManagerRemote msm;		
	
	@EJB
	AgentManagerRemote agm;

	@Override
	public Set<AgentType> getAgentTypes() {
		return agm.getAgentTypes();
	}

	@Override
	public Set<AID> getRunningAgents() {
		return agm.getRunningAgents();
	}

	@Override
	public void startAgent(AgentType type, String name) {
		agm.startAgent(type, name);
		
	}

	@Override
	public void stopAgent(AID aid) {
		agm.stopAgent(aid);
		
	}

	@Override
	public void sendMessage(ACLMessage message) {
		msm.post(message);
		
	}

	@Override
	public List<String> getPerformatives() {
		return msm.getPerformatives();
	}
	
}
