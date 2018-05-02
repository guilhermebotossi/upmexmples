package br.inpe.climaespacial.swd.indexes.v.services;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.repositories.VIndexHourlyAverageReaderRepository;
import br.inpe.climaespacial.swd.indexes.v.repositories.VIndexReaderRepository;
import br.inpe.climaespacial.swd.indexes.v.repositories.VIndexWriterRepository;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Dependent
public class DefaultVIndexService implements VIndexService {

    @Inject
    private VIndexHourlyAverageReaderRepository vIndexHourlyAverageReaderRepository;

    @Inject
    private VIndexReaderRepository vIndexReaderRepository;

    @Inject
    private VIndexCalculator vIndexCalculator;

    @Inject
    private VIndexWriterRepository vIndexWriterRepository;

    @Override
    @Transactional(value = TxType.NOT_SUPPORTED)
    public void calculate() {
        List<HourlyAverage> hal;
        while (!(hal = vIndexHourlyAverageReaderRepository.getHourlyAverages()).isEmpty()) {
            ZonedDateTime zdt = hal.get(hal.size() - 1).getTimeTag();
            List<VIndex> vIndexList = vIndexReaderRepository.getLastVIndexesFromDateTime(zdt);
            VIndex vi = vIndexCalculator.calculate(hal, vIndexList);
            vIndexWriterRepository.save(vi);
        }
    }
}
