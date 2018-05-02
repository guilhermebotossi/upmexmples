package br.inpe.climaespacial.swd.indexes.b.services;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.factories.BIndexFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultBIndexCalculator implements BIndexCalculator {

    private BIndexFactory bIndexFactory;

    @Inject
    public DefaultBIndexCalculator(BIndexFactory bIndexFactory) {
        this.bIndexFactory = bIndexFactory;
    }

    @Override
    public BIndex calculate(HourlyAverage hourlyAverage) {
        throwIfNull(hourlyAverage, "hourlyAverage");

        if (hourlyAverage.getTimeTag() == null) {
            throw new RuntimeException("Propriedade \"hourlyAverage.timeTag\" null.");
        }

        Double bt = hourlyAverage.getBt();
        BIndex bi = bIndexFactory.create();
        bi.setTimeTag(hourlyAverage.getTimeTag());
        bi.setPreValue(bt);

        if (bt != null) {
            int[] ibmin = {0, 8, 15, 20, 30, 40, 80};

            for (int i = 0; i < ibmin.length - 1; i++) {
                if (ibmin[i] <= bt && ibmin[i + 1] > bt) {
                    double indexB = Math.floor((i + (bt - ibmin[i]) / (ibmin[i + 1] - ibmin[i])) * 100) / 100;
                    bi.setPostValue(indexB);
                    break;
                }
            }
        }

        return bi;
    }

}
