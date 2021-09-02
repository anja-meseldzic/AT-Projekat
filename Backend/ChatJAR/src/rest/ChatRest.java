package rest;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import messagemanager.ACLMessage;
import models.AID;
import models.AgentType;
import models.User;

public interface ChatRest {

	@GET
	@Path("/agents/classes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Set<AgentType> getAgentTypes();
	
	@GET
	@Path("/agents/running")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Set<AID> getRunningAgents();
	
	@PUT
	@Path("/agents/running/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void startAgent( AgentType type, @PathParam("name") String name);
	
	@PUT
	@Path("/agents/running")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void stopAgent(AID aid);
	
	@POST
	@Path("/messages")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void sendMessage(ACLMessage message );
	
	@GET
	@Path("/messages")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPerformatives();
	
	
	
	
}
