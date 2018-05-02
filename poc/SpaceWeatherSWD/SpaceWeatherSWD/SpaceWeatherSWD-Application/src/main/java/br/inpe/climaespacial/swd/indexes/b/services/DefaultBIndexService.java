package br.inpe.climaespacial.swd.indexes.b.services;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.repositories.BIndexHourlyAverageReaderRepository;
import br.inpe.climaespacial.swd.indexes.b.repositories.BIndexWriterRepository;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Dependent
public class DefaultBIndexService implements BIndexService {

    @Inject
    private BIndexHourlyAverageReaderRepository bIndexHourlyAverageReaderRepository;

    @Inject
    private BIndexCalculator bIndexCalculator;

    @Inject
    private BIndexWriterRepository bIndexWriterRepository;

    @Override
    @Transactional(value = TxType.NOT_SUPPORTED)
    public void calculate() {
        HourlyAverage ha;
        while ((ha = bIndexHourlyAverageReaderRepository.getHourlyAverage()) != null) {
            BIndex bi = bIndexCalculator.calculate(ha);
            bIndexWriterRepository.save(bi);
        }
    }
}
