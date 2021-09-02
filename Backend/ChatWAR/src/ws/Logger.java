package ws;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import messagemanager.ACLMessage;
import models.AID;



public class Logger {

private Set<Session> sessions = new HashSet<Session>();
	
	@OnOpen
	public void onOpen(Session session) {
		sessions.add(session);
	}
	
	@OnClose
	public void onClose(Session session) {
		sessions.remove(session);
	}
	
	public void send(String message) {
		try {
			for(Session session : sessions)
				session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String ACLToString(ACLMessage message) {
		StringBuilder sb = new StringBuilder();
		sb.append("ACL MESSAGE - ");
		sb.append("\n");
		sb.append("FROM: ");
		sb.append(message.getSender().getType().getName());
		sb.append("-");
		sb.append(message.getSender().getName());
		sb.append("\n");
		sb.append(" TO: ");
		for(AID receiver : message.getReceivers()) {
			sb.append(receiver.getType().getName());
			sb.append("-");
			sb.append(receiver.getName());
			sb.append(" ");
		}
		sb.append("\n");
		sb.append("CONTENT: ");
		sb.append(message.getContent());
		sb.append("\n");
		sb.append("PERFORMATIVE: ");
		sb.append(message.getPerformative().toString());
		sb.append("\n");
		sb.append("USER ARGS: ");
		sb.append(message.getUserArgs().toString());
		return sb.toString();
	}
}
