package br.inpe.climaespacial.swd.indexes.c.services;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.factories.CIndexFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultCIndexCalculator implements CIndexCalculator {

    private CIndexFactory cIndexFactory;

    @Inject
    public DefaultCIndexCalculator(CIndexFactory cIndexFactory) {
        this.cIndexFactory = cIndexFactory;
    }

    @Override
    public CIndex calculate(HourlyAverage hourlyAverage) {
        throwIfNull(hourlyAverage, "hourlyAverage");

        if (hourlyAverage.getTimeTag() == null) {
            throw new RuntimeException("Propriedade \"hourlyAverage.timeTag\" null.");
        }
        CIndex ci = cIndexFactory.create();
        ci.setTimeTag(hourlyAverage.getTimeTag());
        Double cmp = null;

        if (hourlyAverage.getRmp() != null) {
            cmp = 10.6 - hourlyAverage.getRmp();
        }

        ci.setPreValue(cmp);

        if (cmp != null) {
            double[] icmin = {0, 1.500, 2.375, 3.250, 4.125, 5.000, 10.000};

            ci.setPostValue(0.0);
            for (int i = 0; i < icmin.length - 1; i++) {
                if (cmp >= icmin[i] && cmp < icmin[i + 1]) {
                    double indexC = (double) i + (cmp - icmin[i]) / (icmin[i + 1] - icmin[i]);

                    ci.setPostValue(indexC);
                    break;
                }
            }
        }

        return ci;
    }

}
