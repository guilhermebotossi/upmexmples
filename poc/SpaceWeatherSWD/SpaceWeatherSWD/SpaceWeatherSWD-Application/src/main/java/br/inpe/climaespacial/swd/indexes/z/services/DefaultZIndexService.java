package br.inpe.climaespacial.swd.indexes.z.services;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.repositories.ZIndexHourlyAverageReaderRepository;
import br.inpe.climaespacial.swd.indexes.z.repositories.ZIndexWriterRepository;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Dependent
public class DefaultZIndexService implements ZIndexService {

    @Inject
    private ZIndexHourlyAverageReaderRepository zIndexHourlyAverageReaderRepository;

    @Inject
    private ZIndexCalculator zIndexCalculator;

    @Inject
    private ZIndexWriterRepository zIndexWriterRepository;

    @Override
    @Transactional(value = TxType.NOT_SUPPORTED)
    public void calculate() {
        List<HourlyAverage> hal;
        while (!(hal = zIndexHourlyAverageReaderRepository.getHourlyAverages()).isEmpty()) {
            ZIndex zi = zIndexCalculator.calculate(hal);
            zIndexWriterRepository.save(zi);
        }
    }
}
