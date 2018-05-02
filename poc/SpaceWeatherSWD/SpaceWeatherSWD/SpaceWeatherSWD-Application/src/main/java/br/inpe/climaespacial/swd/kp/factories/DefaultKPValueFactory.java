package br.inpe.climaespacial.swd.kp.factories;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;

@Dependent
public class DefaultKPValueFactory implements KPValueFactory {
    
    @Inject
    private HelperFactory<KPValue> kpValueHelperFactory;

    @Override
    public KPValue create(ZonedDateTime timeTag, Long kpValue, KPValueFlag flag) {
        throwIfNull(timeTag, "timeTag");
        throwIfNull(kpValue, "kpValue");
        throwIfNull(flag, "flag");
        
        KPValue kpv = kpValueHelperFactory.create();
        kpv.setTimeTag(timeTag);
        kpv.setKPValue(kpValue);
        kpv.setKPValueFlag(flag);
        
        return kpv;
    }

}
