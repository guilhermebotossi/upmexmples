package br.poc.producer;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@RequestScoped
public class JmsQueueProducerImpl implements JmsQueueProducer {

	@Resource(lookup = "jms/ConnectionFactory")
	private static ConnectionFactory connectionFactory;
	
	@Resource(lookup = "jms/Queue")
	private static Queue queue;
	
	@Override
	public void enqueue(String base64Content) throws JMSException {
		Session session = getSession();
		MessageProducer producer = session.createProducer(queue);
		TextMessage message = session.createTextMessage();
		message.setText(base64Content);
		producer.send(message);
	}

	private Session getSession() throws JMSException {
		try(Connection connection = connectionFactory.createConnection()) { 
			return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		}
	}

}
