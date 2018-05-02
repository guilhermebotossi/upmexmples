package br.inpe.climaespacial.swd.kp.values.services;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.values.dtos.KPForecastData;
import br.inpe.climaespacial.swd.kp.values.dtos.KPForecastEntry;
import br.inpe.climaespacial.swd.kp.values.dtos.KPValueEntry;
import br.inpe.climaespacial.swd.kp.values.dtos.BaseKPForecastEntry;
import br.inpe.climaespacial.swd.kp.values.factories.KPForecastDataFactory;
import br.inpe.climaespacial.swd.kp.values.mappers.KPForecastEntryMapper;
import br.inpe.climaespacial.swd.kp.values.mappers.KPValueEntryMapper;
import br.inpe.climaespacial.swd.kp.values.repositories.KPForecastReaderRepository;
import br.inpe.climaespacial.swd.kp.values.repositories.KPValueReaderRepository;

@Dependent
public class DefaultKPForecastValuesService implements KPForecastValuesService {

    @Inject
    private KPValueReaderRepository kpReaderRepository;

    @Inject
    private KPForecastReaderRepository kpForecastReaderRepository;

    @Inject
    private KPForecastEntryMapper kpForecastEntryMapper;

    @Inject
    private KPValueEntryMapper kpValueEntryMapper;

    @Inject
    private KPForecastDataFactory kpForecastDataFactory;

    @Inject
    private ListFactory<BaseKPForecastEntry> kpForecastEntryList;

    @Inject
    private DateTimeProvider dateTimeProvider;

    @Override
    @Transactional
    public KPForecastData list() {
        KPForecastData kpForecastData;
        List<KPValue> kpvl = kpReaderRepository.getLastKPIndexes();
        List<BaseKPForecastEntry> kpfel3 = kpForecastEntryList.create();

        if (kpvl.size() != 0) {

            KPValue kpv1 = kpvl.stream().max(Comparator.comparing(kp -> kp.getTimeTag())).get();

            List<KPForecast> kpfl = kpForecastReaderRepository.getNextForecasts(kpv1.getTimeTag());

            List<KPValueEntry> kpfel1 = kpValueEntryMapper.mapKPValue(kpvl);
            ZonedDateTime now = dateTimeProvider.getNow();
            KPForecast kpf = kpfl.isEmpty() ? null : kpfl.get(kpfl.size() - 1);

            if (kpf != null && !kpf.getPredictedTimeTag().isAfter(now)) {
                //kpfl.clear();
            }
            List<KPForecastEntry> kpfel2 = kpForecastEntryMapper.mapKPForecast(kpfl);

            kpfel3.addAll(kpfel1);
            kpfel3.addAll(kpfel2);
 
            kpForecastData = kpForecastDataFactory.create(kpfel3);
        } else {
            kpForecastData = kpForecastDataFactory.create(kpfel3);
        }

        return kpForecastData;
    }

}
