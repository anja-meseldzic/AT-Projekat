package util;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import models.AgentCenter;

@Singleton
@Remote(AgentCenterRemote.class)
@Startup
public class AgentCenterManager implements AgentCenterRemote{

	private static final long serialVersionUID = 1L;

	private AgentCenter host;
	
	@Override
	public void setLocalNodeInfo() {
		String nodeAddress = getNodeAddress();
		String nodeAlias = getNodeAlias() + ":8081";
		this.host = new AgentCenter(nodeAddress, nodeAlias);;
		System.out.println("node alias: " + this.host.getAlias() + ", node address: " + 
		this.host.getAddress());
	}
	
	private String getNodeAddress() {
		try {
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			ObjectName http = new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http");
			return (String) mBeanServer.getAttribute(http, "boundAddress");
		} catch (MalformedObjectNameException | InstanceNotFoundException | AttributeNotFoundException | ReflectionException | MBeanException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private String getNodeAlias() {
		return System.getProperty("jboss.node.name");
	}

	@Override
	public AgentCenter getHost() {
		return host;
	}

}
