package br.poc.producer;

import javax.jms.JMSException;

public interface JmsQueueProducer {
	
	public void enqueue(String base64Content) throws JMSException;

}
