package connectionmanager;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.Path;

import agentmanager.AgentManagerRemote;
import util.AgentCenterRemote;



@Singleton
@Startup
@Remote(ConnectionManagerRemote.class)
@Path("/connection")
public class ConnectionManager implements ConnectionManagerRemote {
	

	@EJB AgentCenterRemote acm;
	@EJB AgentManagerRemote agm;
	
	private String hostAlias;
	
	@PostConstruct
	private void init() {
		setLocalNodeInfo();
		hostAlias = acm.getHost().getAlias();
		
	}
	
	private void setLocalNodeInfo() {
		acm.setLocalNodeInfo();
		//acm.setMasterAlias();
	}
}
