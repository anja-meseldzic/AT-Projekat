package agentmanager;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import agents.Agent;
import agents.UserAgent;
import models.AID;
import models.AgentType;
import util.AgentCenterRemote;
import util.JNDILookup;
import ws.WSEndPoint;

/**
 * Session Bean implementation class AgentManagerBean
 */
@Singleton
@Remote(AgentManagerRemote.class)
@LocalBean
public class AgentManagerBean implements AgentManagerRemote {

	private static final long serialVersionUID = 1L;

	Set<Agent> runningAgents = new HashSet<Agent>();

	@EJB
	AgentCenterRemote acm;
	@EJB
	WSEndPoint agentSocket;

	@Override
	public void startAgent(AgentType type, String name) {

		Agent agent = (Agent) JNDILookup
				.lookUp(type.getModule() + type.getName() + "!" + Agent.class.getName() + "?stateful", Agent.class);

		if (agent != null) {
			agent.init(new models.AID(name, acm.getHost(), type));
			System.out.println(agent.getAID());
			System.out.println(agent.getClass().getCanonicalName());
			if (runningAgents.stream().noneMatch(a -> a.getAID().equals(agent.getAID()))) {
				runningAgents.add(agent);
				updateViaSocket();
			}
		}
	}

	private Set<AgentType> getLocalAgentTypes() {
		Set<AgentType> types = new HashSet<AgentType>();
		types.add(new AgentType(UserAgent.class.getSimpleName(), JNDILookup.JNDIPathChat, acm.getHost().getAddress()));
		return types;
	}

	@Override
	public void stopAgent(AID aid) {
		System.out.println(aid);
		runningAgents.removeIf(a -> a.getAID().equals(aid));
		updateViaSocket();
	}

	@Override
	public Set<AID> getRunningAgents() {
		Set<AID> agents = runningAgents.stream().map(a -> a.getAID()).collect(Collectors.toSet());
		return agents;
	}

	@Override
	public Set<AgentType> getAgentTypes() {
		Set<AgentType> types = new HashSet<AgentType>();
		types.addAll(getLocalAgentTypes());
		return types;
	}

	@Override
	public Agent getRunningAgentByAID(AID aid) {
		return runningAgents.stream().filter(a -> a.getAID().equals(aid)).findFirst().orElse(null);
	}
	
	private void updateViaSocket() {
	    try {
	    	Set<AID> agents = getRunningAgents();
			ObjectMapper mapper = new ObjectMapper();
			String agentsJSON = mapper.writeValueAsString(agents);
			agentSocket.send(agentsJSON);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	

}
