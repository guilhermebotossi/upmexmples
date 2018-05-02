package br.inpe.climaespacial.swd.controllers;

import br.inpe.climaespacial.swd.hello.dtos.Hello;
import br.inpe.climaespacial.swd.hello.services.HelloService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Stateless
@Path("/hello")
public class HelloResource {

    @Inject
    HelloService helloService;

    @GET
    public Response hello() {
        Hello h = helloService.hello();

        return Response.status(200).entity(h).build();
    }

}
