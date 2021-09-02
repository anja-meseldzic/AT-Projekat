package util;

import java.io.Serializable;
import java.util.List;

import models.AgentCenter;

public interface AgentCenterRemote extends Serializable {

	public void setLocalNodeInfo();
	
	public AgentCenter getHost();
	
}
