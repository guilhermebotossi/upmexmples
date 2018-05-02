package br.inpe.climaespacial.swd.average;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.repositories.HourlyAverageWriterRepository;
import br.inpe.climaespacial.swd.average.repositories.MagPlasmaCalculatedReaderRepository;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;
import java.time.ZonedDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Dependent
public class DefaultAverage implements Average {

    @Inject
    private MagPlasmaCalculatedReaderRepository magPlasmaCalculatedReaderRepository;

    @Inject
    private HourlyAverageCalculator hourlyAverageCalculator;

    @Inject
    private HourlyAverageWriterRepository hourlyAverageWriterRepository;

    @Override
    @Transactional(value = TxType.NOT_SUPPORTED)
    public void calculate() {
        while (true) {
            SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>> se = magPlasmaCalculatedReaderRepository.list();
            if (se.getKey() != null) {
                HourlyAverage ha = hourlyAverageCalculator.calculate(se.getKey(), se.getValue());
                hourlyAverageWriterRepository.save(ha);
            } else {
                break;
            }
        }
    }

}
