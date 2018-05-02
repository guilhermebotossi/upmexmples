package br.inpe.climaespacial.swd.indexes;

import br.inpe.climaespacial.swd.commons.factories.Factory;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultRangeDateFactory implements RangeDateFactory {

    @Inject
    private Factory<RangeDate> rangeDateFactory;

    @Override
    public RangeDate create(ZonedDateTime firstAcquisitionDate, ZonedDateTime lastAcquisitionDate) {

        RangeDate rd = rangeDateFactory.create();
        rd.setMinDate(firstAcquisitionDate);
        rd.setMaxDate(lastAcquisitionDate);
        return rd;
    }

}
