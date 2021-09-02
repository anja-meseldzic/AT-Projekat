package messagemanager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import models.Performative;

/**
 * Session Bean implementation class MessageManagerBean
 */
@Stateless
@LocalBean
public class MessageManagerBean implements MessageManagerRemote {

	/**
	 * Default constructor.
	 */
	public MessageManagerBean() {
	}

	@EJB
	private JMSFactory factory;

	private Session session;
	private MessageProducer defaultProducer;

	@PostConstruct
	public void postConstruct() {
		session = factory.getSession();
		defaultProducer = factory.getProducer(session);
	}

	@PreDestroy
	public void preDestroy() {
		try {
			session.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}



	private Message createJMSMessage(ACLMessage message) {
		ObjectMessage jmsMessage = null;
		try {
			jmsMessage = session.createObjectMessage(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return jmsMessage;
	}

	@Override
	public void post(ACLMessage msg) {
		try {
			defaultProducer.send(createJMSMessage(msg));
			System.out.println("Entered message manager");
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<String> getPerformatives() {
		List<String> result = new ArrayList<String>();
		for(Performative p : Performative.values())
			result.add(p.toString());
		return result;
	}

}
