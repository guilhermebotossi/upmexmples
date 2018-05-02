package br.inpe.climaespacial.swd.controllers;

import br.inpe.climaespacial.swd.home.dtos.IndexData;
import br.inpe.climaespacial.swd.home.services.IndexesService;
import br.inpe.climaespacial.swd.indexes.RangeDate;
import java.time.ZonedDateTime;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Stateless
@Path("/idx")
public class IndexResource {

    @Inject
    private IndexesService indexesService;

    @GET
    @Path("/rangeDate")
    public RangeDate getIndexesRangeDate() {
        RangeDate rd = indexesService.getRangeDate();
        return rd;
    }

    @GET
    @Path("/max")
    public IndexData getMaxSummaryData(
            @QueryParam("minData") ZonedDateTime minDate,
            @QueryParam("maxData") ZonedDateTime maxDate
    ) {
        IndexData id = indexesService.getIndexData(minDate, maxDate);
        return id;
    }

}
