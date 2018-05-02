package br.inpe.climaespacial.swd.kp.forecast.services;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.forecast.repositories.CIndexReaderRepository;
import br.inpe.climaespacial.swd.kp.forecast.repositories.KPForecastWriterRepository;
import br.inpe.climaespacial.swd.kp.forecast.repositories.KPValueReaderRepository;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Dependent
public class DefaultKPForecastService implements KPForecastService {
    
    @Inject
    private CIndexReaderRepository cIndexReaderRepository; 
    
    @Inject
    private KPValueReaderRepository kpValueReaderRepository; 
    
    @Inject
    private KPForecastCalculator kpForecastCalculator;
    
    @Inject
    private KPForecastWriterRepository kpForecastWriterRepository;
    
    @Override
    @Transactional
    public void calculate() {
        KPValue kpValue = null;
        
        while((kpValue = kpValueReaderRepository.getLastKPValue()) != null) {
            List<CIndex> cIndexList = cIndexReaderRepository.getLastCIndexes(kpValue.getTimeTag());
            
            List<KPForecast> kpfl = kpForecastCalculator.calculate(cIndexList, kpValue);
            
            kpForecastWriterRepository.save(kpfl);
        }
    }

}
