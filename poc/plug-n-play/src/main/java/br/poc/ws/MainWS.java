package br.poc.ws;

import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.poc.utils.Utils;

@Path(value = "/")	
public class MainWS {

	@GET
	public void getWSData(@PathParam("params") String base64Content) throws Exception {
		Utils.enqueue(base64Content);
	}

}
