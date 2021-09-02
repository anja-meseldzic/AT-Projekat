package agentmanager;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import agents.Agent;
import models.AID;

import models.AgentType;


public interface AgentManagerRemote extends Serializable {
	
	public void startAgent(AgentType type, String name);
	
	public void stopAgent(AID aid);
	
	public Set<AID> getRunningAgents();
	
	public Set<AgentType> getAgentTypes();
	
	public Agent getRunningAgentByAID(AID aid);

}
