package br.inpe.climaespacial.swd.kp.forecast.mappers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;
import br.inpe.climaespacial.swd.kp.forecast.factories.KPForecastEntityFactory;

@Dependent
public class DefaultKPForecastEntityMapper implements KPForecastEntityMapper {

    @Inject
    private ListFactory<KPForecastEntity> kpForecastEntityListFactory;
    
    @Inject
    private KPForecastEntityFactory kpForecastEntityFactory;
    
    @Override
    public List<KPForecastEntity> map(List<KPForecast> kpForecastList) {
        throwIfNull(kpForecastList, "kpForecastList");
        
        List<KPForecastEntity> kpfel = kpForecastEntityListFactory.create();
        
        kpForecastList.forEach(kpf -> {kpfel.add(createKPForecastEntity(kpf));});
        
        return kpfel;
    }
    
    private KPForecastEntity createKPForecastEntity(KPForecast kpf) {
        KPForecastEntity kpfe = kpForecastEntityFactory.create();
        kpfe.setPredictedTimeTag(kpf.getPredictedTimeTag());
        kpfe.setIndexesTimeTag(kpf.getIndexesTimeTag());
        kpfe.setProbability1(kpf.getProbability1());
        kpfe.setProbability2(kpf.getProbability2());
        kpfe.setProbability3(kpf.getProbability3());
        kpfe.setInferiorLimit1(kpf.getInferiorLimit1());
        kpfe.setInferiorLimit2(kpf.getInferiorLimit2());
        kpfe.setInferiorLimit3(kpf.getInferiorLimit3());
        kpfe.setUpperLimit1(kpf.getUpperLimit1());
        kpfe.setUpperLimit2(kpf.getUpperLimit2());
        kpfe.setUpperLimit3(kpf.getUpperLimit3());
        
        return kpfe;
    }

}

