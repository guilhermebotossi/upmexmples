package br.inpe.climaespacial.swd.kp.values.mappers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;
import br.inpe.climaespacial.swd.kp.forecast.factories.KPForecastFactory;

@Dependent
public class DefaultKPForecastMapper implements KPForecastMapper {
    
    @Inject
    private KPForecastFactory kpForecastFactory;
    
    @Inject
    private ListFactory<KPForecast> kpForecastList;

    @Override
    public List<KPForecast> map(List<KPForecastEntity> kpForecastEntityList) {
        throwIfNull(kpForecastEntityList, "kpForecastEntityList");
        
        List<KPForecast> kpfl = kpForecastList.create();
        kpForecastEntityList.forEach(kpfe -> kpfl.add(createKPForecast(kpfe)));
        
        return kpfl;
    }

    private KPForecast createKPForecast(KPForecastEntity kpfe) {
        KPForecast kpf = kpForecastFactory.create();
        kpf.setPredictedTimeTag(kpfe.getPredictedTimeTag());
        kpf.setIndexesTimeTag(kpfe.getIndexesTimeTag());
        kpf.setProbability1(kpfe.getProbability1());
        kpf.setProbability2(kpfe.getProbability2());
        kpf.setProbability3(kpfe.getProbability3());
        kpf.setInferiorLimit1(kpfe.getInferiorLimit1());
        kpf.setInferiorLimit2(kpfe.getInferiorLimit2());
        kpf.setInferiorLimit3(kpfe.getInferiorLimit3());
        kpf.setUpperLimit1(kpfe.getUpperLimit1());
        kpf.setUpperLimit2(kpfe.getUpperLimit2());
        kpf.setUpperLimit3(kpfe.getUpperLimit3());
        return kpf;
    }

}
