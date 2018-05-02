package br.inpe.climaespacial.swd.kp.mappers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;
import br.inpe.climaespacial.swd.kp.factories.KPValueEntityFactory;

@Dependent
public class DefaultKPValueEntityMapper implements KPValueEntityMapper {

    @Inject
    private ListFactory<KPValueEntity> kpValueEntityListFactory;
    
    @Inject
    private KPValueEntityFactory kpValueEntityFactory;
    
    @Override
    public List<KPValueEntity> map(List<KPValue> kpValueList, KPEntity kpEntity) {
        throwIfNull(kpValueList, "kpValueList");
        throwIfNull(kpEntity, "kpEntity");
        
        List<KPValueEntity> kpvel = kpValueEntityListFactory.create();
        
        for(KPValue kpv : kpValueList) {
            if(kpv.getTimeTag() == null) {
                throw new RuntimeException("Property \"KPValue.timeTag\" null.");
            }
            KPValueEntity kpve = kpValueEntityFactory.create();
            kpve.setTimeTag(kpv.getTimeTag());
            kpve.setKPValue(kpv.getKPValue());
            kpve.setKPValueFlag(kpv.getKPValueFlag());
            kpve.setKPEntity(kpEntity);
            kpvel.add(kpve);
        }
        return kpvel;
    }

}
