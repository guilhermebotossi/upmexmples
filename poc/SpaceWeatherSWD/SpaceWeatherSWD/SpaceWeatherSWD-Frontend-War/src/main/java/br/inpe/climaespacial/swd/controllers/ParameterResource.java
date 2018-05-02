package br.inpe.climaespacial.swd.controllers;

import br.inpe.climaespacial.swd.indexes.RangeDate;
import br.inpe.climaespacial.swd.values.dtos.ValuesData;
import br.inpe.climaespacial.swd.values.services.ValuesService;
import java.time.ZonedDateTime;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Stateless
@Path("/param")
public class ParameterResource {

    @Inject
    private ValuesService valuesService;

    @GET
    @Path("/parameters")
    public ValuesData getParametersData(
            @QueryParam("minData") ZonedDateTime minDate,
            @QueryParam("maxData") ZonedDateTime maxDate
    ) {
        ValuesData vd = valuesService.getValuesData(minDate, maxDate);
        return vd;
    }

    @GET
    @Path("/rangeDate")
    public RangeDate getParametersRangeDate() {
        RangeDate rd = valuesService.getRangeDate();

        return rd;
    }
}
