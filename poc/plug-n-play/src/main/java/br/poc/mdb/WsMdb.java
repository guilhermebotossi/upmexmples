package br.poc.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(name = "WsMdb", activationConfig = {
 @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
 @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/WsQueue"),
 @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"), 
 @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1")
})
 
public class WsMdb implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			String body = message.getBody(String.class);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
