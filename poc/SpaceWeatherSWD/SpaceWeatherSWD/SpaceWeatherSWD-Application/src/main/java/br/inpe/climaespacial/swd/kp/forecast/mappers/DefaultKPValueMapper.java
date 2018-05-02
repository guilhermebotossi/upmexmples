package br.inpe.climaespacial.swd.kp.forecast.mappers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;
import br.inpe.climaespacial.swd.kp.factories.KPValueFactory;
import br.inpe.climaespacial.swd.kp.forecast.repositories.KPValueMapper;

@Dependent
public class DefaultKPValueMapper implements KPValueMapper {

    @Inject
    private KPValueFactory kpValueFactory;
    
    @Inject
    private ListFactory<KPValue> kpValueListFactory;
    
    @Override
    public KPValue map(KPValueEntity kpValueEntity) {
        KPValue kpv = null ;
        if(kpValueEntity != null) {
            kpv = kpValueFactory.create(kpValueEntity.getTimeTag(), kpValueEntity.getKPValue(), kpValueEntity.getKPValueFlag());
        }
        
        return kpv;
    }

    @Override
    public List<KPValue> map(List<KPValueEntity> kpValueEntityList) {
        throwIfNull(kpValueEntityList, "kpValueEntityList");
        
        List<KPValue> kpvl = kpValueListFactory.create();
        
        kpValueEntityList.forEach(kpve -> {
            KPValue kpv = null ;
            if(kpve != null) {
                kpv = kpValueFactory.create(kpve.getTimeTag(), kpve.getKPValue(), kpve.getKPValueFlag());
            }
            kpvl.add(kpv);
        });
        
        
        return kpvl;
    }

}
