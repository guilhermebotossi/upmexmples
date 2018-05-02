package br.inpe.climaespacial.swd.controllers;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.inpe.climaespacial.swd.kp.values.dtos.KPForecastData;
import br.inpe.climaespacial.swd.kp.values.services.KPForecastValuesService;

@Stateless
@Path("/kp")
public class KPResource {

    @Inject
    private KPForecastValuesService kpForecastValuesService;
    
    @GET
    @Path("/forecast")
    public KPForecastData getKpForecastData() {
        return kpForecastValuesService.list();
    }
}
