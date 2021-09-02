package ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import agentmanager.AgentManagerBean;
import agentmanager.AgentManagerRemote;
import chatmanager.ChatManagerBean;
import chatmanager.ChatManagerRemote;
import models.User;
import util.JNDILookup;

@Singleton
@ServerEndpoint("/ws/agent")
@LocalBean
public class WSEndPoint {

	static Set<Session> sessions = new HashSet<Session>();

	@OnOpen
	public void onOpen(Session session) {
		sessions.add(session);
	}

	@OnClose
	public void close(Session session) {
		sessions.remove(session);
	}

	public void send(Session session, String message) {

		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
