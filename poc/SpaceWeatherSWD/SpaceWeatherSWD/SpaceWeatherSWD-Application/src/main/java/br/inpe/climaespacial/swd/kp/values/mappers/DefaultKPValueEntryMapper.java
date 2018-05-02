package br.inpe.climaespacial.swd.kp.values.mappers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.values.dtos.KPValueEntry;
import br.inpe.climaespacial.swd.kp.values.factories.KPValueEntryFactory;

@Dependent
public class DefaultKPValueEntryMapper implements KPValueEntryMapper {

    @Inject
    private ListFactory<KPValueEntry> kpValueEntryListFactory;

    @Inject
    private KPValueEntryFactory kpValueEntryFactory;


    @Override
    public List<KPValueEntry> mapKPValue(List<KPValue> kpValueList) {
        throwIfNull(kpValueList, "kpValueList");

        List<KPValueEntry> kpfel = kpValueEntryListFactory.create();

        kpValueList.forEach(kpv -> {
            KPValueEntry kp = kpValueEntryFactory.create();
            kp.setTimeTag(kpv.getTimeTag());
            kp.setPresentationTimeTag(kpv.getTimeTag().minusMinutes(90));
            kp.setValue(kpv.getKPValue().doubleValue());
            assert kp.isForecast() == false;
            kpfel.add(kp);
        });

        return kpfel;
    }

}
