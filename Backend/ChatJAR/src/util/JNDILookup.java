package util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import agentmanager.AgentManagerBean;
import agentmanager.AgentManagerRemote;
import agents.Agent;
import agents.UserAgent;
import chatmanager.ChatManagerBean;
import chatmanager.ChatManagerRemote;

import messagemanager.MessageManagerBean;
import messagemanager.MessageManagerRemote;

public abstract class JNDILookup {

	public static final String JNDIPathChat = "ejb:ChatEAR/ChatJAR//";

	@SuppressWarnings("unchecked")
	public static <T> T lookUp(String name, Class<T> c) {
		T bean = null;
		try {
			Context context = new InitialContext();

			System.out.println("Looking up: " + name);
			bean = (T) context.lookup(name);

			context.close();

		} catch (NamingException e) {
			e.printStackTrace();
		}
		return bean;
	}
}
