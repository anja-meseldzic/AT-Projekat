package agents;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.jms.JMSException;

import agentmanager.AgentManagerBean;
import agentmanager.AgentManagerRemote;
import chatmanager.ChatManagerRemote;

import messagemanager.ACLMessage;
import messagemanager.MessageManagerRemote;



import models.AID;
import models.AgentType;
import models.Message;
import models.Performative;
import models.User;
import util.AgentCenterRemote;
import util.JNDILookup;
import ws.Logger;
import ws.WSEndPoint;


@Stateful
@Remote(Agent.class)
public class UserAgent extends models.Agent {

	private static final long serialVersionUID = 1L;

	@EJB
	ChatManagerRemote chm;
	@EJB
	AgentManagerRemote agm;
	@EJB
	AgentCenterRemote acm;
	@EJB
	MessageManagerRemote msm;
	@EJB
	Logger logger;

	@Override
	public void handleMessage(ACLMessage msg) {
		Performative option = msg.getPerformative();

		switch (option) {

		case REGISTER: {
			String username = msg.getUserArg("username").toString();
			String password = msg.getUserArg("password").toString();
			register(username, password);

			break;
		}
		case LOG_IN: {
			String username = msg.getUserArg("username").toString();
			String password = msg.getUserArg("password").toString();
			logIn(username, password);

			break;
		}
		case LOG_OUT: {
			String username = msg.getUserArg("username").toString();
			logOut(username);
			break;
		}
		case REGISTERED_LIST: {
			String username = msg.getUserArg("username").toString();
			getRegistered(username);
			break;
		}
		case LOGGED_IN_LIST: {
			String username = msg.getUserArg("username").toString();
			getLoggedIn(username);
			break;
		}
		case SEND_MESSAGE_ALL: {
			String subject = msg.getUserArg("subject").toString();
			String content = msg.getUserArg("content").toString();
			String sender = msg.getUserArg("sender").toString();
			sendMessageToAll(sender, subject, content);
			break;
		}
		case SEND_MESSAGE_USER: {
			String subject = msg.getUserArg("subject").toString();
			String content = msg.getUserArg("content").toString();
			String sender = msg.getUserArg("sender").toString();
			String receiver = msg.getUserArg("receiver").toString();
			sendMessage(sender, receiver, subject, content);
			break;
		}
		case GET_MESSAGES: {
			String username = msg.getUserArg("username").toString();
			getAllMessages(username);
			break;
		}
		case PERFORMED: {
			AID agentToStop = msg.getSender();
			agm.stopAgent(agentToStop);
			break;
		}
		default:
			return;
		}
	}


	private void logIn(String username, String password) {
		boolean success = chm.logIn(username, password);
		if (success) {
			logger.send("User with username " + username + " successfully logged in");
		} else
			logger.send("User with username " + username + " doesn't exist or the password is incorrect");
	}

	private void register(String username, String password) {
		boolean success = chm.register(username, password);
		if (success) {
			logger.send("User with username " + username + " successfully registered");
		} else
			logger.send("User with username " + username + " already exists");
	}

	private void logOut(String username) {
		if (loggedIn(username)) {
			chm.logOut(username);
			logger.send("User with username " + username + " successfully logged out");
		}
	}

	private void getLoggedIn(String username) {
		if (loggedIn(username)) {
			List<User> users = chm.getLoggedIn();
			logger.send("Logged in users: " + users);
		}
	}

	private void getRegistered(String username) {
		if (loggedIn(username)) {
			List<User> users = chm.getRegistered();
			logger.send("Registered users: " + users);
		}
	}

	private void sendMessage(String sender, String receiver, String subject, String content) {
		if (loggedIn(sender)) {
			if (!chm.existsRegistered(receiver)) {
				logger.send("Reciever with username " + receiver + " doesn't exist");
				return;
			}
			Message message = chm.saveMessage(sender, receiver, subject, content);
			logger.send("Message: " + message + " sent");
		}
	}

	private void sendMessageToAll(String sender, String subject, String content) {
		if (loggedIn(sender))
			for (User user : chm.getLoggedIn())
				sendMessage(sender, user.getUsername(), subject, content);
	}

	private void getAllMessages(String username) {
		if (loggedIn(username)) {
			List<Message> messages = chm.getMessages(username);
			logger.send("Messages for user with username " + username + ": " + messages);
		}
	}

	private boolean loggedIn(String username) {
		if (!chm.existsLoggedIn(username)) {
			logger.send("User with username " + username + " is not logged in");
			return false;
		}
		return true;
	}


	

}
