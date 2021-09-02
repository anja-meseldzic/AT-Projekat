package messagemanager;

import java.util.List;

import javax.ejb.Remote;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import models.Performative;

@Remote
public interface MessageManagerRemote {
	
	public void post(ACLMessage msg);
	public List<String> getPerformatives();
}
