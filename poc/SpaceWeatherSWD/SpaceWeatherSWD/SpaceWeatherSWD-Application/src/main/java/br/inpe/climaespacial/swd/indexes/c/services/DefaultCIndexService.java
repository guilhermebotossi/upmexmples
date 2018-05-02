package br.inpe.climaespacial.swd.indexes.c.services;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.repositories.CIndexHourlyAverageReaderRepository;
import br.inpe.climaespacial.swd.indexes.c.repositories.CIndexWriterRepository;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Dependent
public class DefaultCIndexService implements CIndexService {

    @Inject
    private CIndexHourlyAverageReaderRepository cIndexHourlyAverageReaderRepository;

    @Inject
    private CIndexCalculator cIndexCalculator;

    @Inject
    private CIndexWriterRepository cIndexWriterRepository;

    @Override
    @Transactional
    public void calculate() {
        HourlyAverage ha;
        while ((ha = cIndexHourlyAverageReaderRepository.getHourlyAverage()) != null) {
            CIndex ci = cIndexCalculator.calculate(ha);
            cIndexWriterRepository.save(ci);
        }
    }
}
