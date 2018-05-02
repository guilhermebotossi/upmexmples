package br.inpe.climaespacial.swd.indexes.z.services;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.factories.ZIndexFactory;

import static java.lang.Math.abs;
import static java.lang.Math.floor;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultZIndexCalculator implements ZIndexCalculator {

    private ZIndexFactory zIndexFactory;
    
    @Inject
    public DefaultZIndexCalculator(ZIndexFactory zIndexFactory) {
        this.zIndexFactory = zIndexFactory;
    }

    @Override
    public ZIndex calculate(List<HourlyAverage> hourlyAverageList) {
        if (hourlyAverageList == null || hourlyAverageList.isEmpty()) {
            throw new RuntimeException("Parametro \"hourlyAverageList\" null/empty.");
        }

        Optional<HourlyAverage> hao = hourlyAverageList.stream().filter(ha -> ha.getTimeTag() == null).findAny();

        if (hao.isPresent()) {
            throw new RuntimeException("Propriedade \"hourlyAverage.timeTag\" null.");
        }

        Double bz = getBzAverage(hourlyAverageList);
        ZIndex zi = zIndexFactory.create();
        zi.setTimeTag(getLastTimeTag(hourlyAverageList));

        zi.setPreValue(bz);

        if (bz != null) {
            Double absBz = Math.abs(bz);
            int[] izmin = {0, -4, -7, -10, -15, -20, -40};
            if (bz >= 0.0) {
                zi.setPostValue(0.0);
            } else if (bz <= -40) {
                zi.setPostValue(5.99);
            } else {
                for (int i = 0; i < izmin.length - 1; i++) {
                    if (absBz >= abs(izmin[i]) && absBz < abs(izmin[i + 1])) {
                        double indexZ = i + (absBz - abs(izmin[i])) / (abs(izmin[i + 1]) - abs(izmin[i]));
                        indexZ = floor(indexZ * 100) / 100;
                        zi.setPostValue(indexZ);
                        break;
                    }
                }
            }
        }

        return zi;
    }

    private ZonedDateTime getLastTimeTag(List<HourlyAverage> hourlyAverageList) {
        int size = hourlyAverageList.size();

        return hourlyAverageList.get(size - 1).getTimeTag();
    }

    private Double getBzAverage(List<HourlyAverage> hourlyAverageList) {
        OptionalDouble od = hourlyAverageList.stream().filter(ha -> ha.getBzGsm() != null).mapToDouble(ha -> ha.getBzGsm()).average();
        return od.isPresent() ? od.getAsDouble() : null;
    }
}
