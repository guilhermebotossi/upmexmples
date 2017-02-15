package br.poc.ws;

import javax.inject.Inject;
import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.poc.producer.JmsQueueProducer;

@Path(value = "/")	
public class MainWS {
	
	@Inject
	private JmsQueueProducer producer;

	@GET
	public void getWSData(@PathParam("params") String base64Content) throws Exception {
		producer.enqueue(base64Content);
	}

}
