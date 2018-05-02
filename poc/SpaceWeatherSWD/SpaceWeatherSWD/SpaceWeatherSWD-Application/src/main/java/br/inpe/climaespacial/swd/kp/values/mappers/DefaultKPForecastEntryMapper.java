package br.inpe.climaespacial.swd.kp.values.mappers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.values.dtos.KPForecastEntry;
import br.inpe.climaespacial.swd.kp.values.factories.KPForecastEntryFactory;

@Dependent
public class DefaultKPForecastEntryMapper implements KPForecastEntryMapper {

    @Inject
    private ListFactory<KPForecastEntry> kpForecastEntryListFactory;

    @Inject
    private KPForecastEntryFactory kpForecastEntryFactory;


    @Override
    public List<KPForecastEntry> mapKPForecast(List<KPForecast> kpForecastList) {

        throwIfNull(kpForecastList, "kpForecastList");

        List<KPForecastEntry> kpfel = kpForecastEntryListFactory.create();

        kpForecastList.forEach(kpf -> {
            KPForecastEntry kpfe = kpForecastEntryFactory.create();
            kpfe.setTimeTag(kpf.getPredictedTimeTag());
            kpfe.setPresentationTimeTag(kpf.getPredictedTimeTag().minusMinutes(90));
            kpfe.setForecast(true);
            kpfe.setProbability1(kpf.getProbability1());
            kpfe.setProbability2(kpf.getProbability2());
            kpfe.setProbability3(kpf.getProbability3());
            kpfe.setInferiorLimit1(kpf.getInferiorLimit1());
            kpfe.setInferiorLimit2(kpf.getInferiorLimit2());
            kpfe.setInferiorLimit3(kpf.getInferiorLimit3());
            kpfe.setUpperLimit1(kpf.getUpperLimit1());
            kpfe.setUpperLimit2(kpf.getUpperLimit2());
            kpfe.setUpperLimit3(kpf.getUpperLimit3());
            
            kpfel.add(kpfe);
        });

        return kpfel;
    }

}
