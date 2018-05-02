package br.inpe.climaespacial.swd.kp.mappers;

import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.factories.KPEntityFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultKPEntityMapper implements KPEntityMapper {

    @Inject
    private KPEntityFactory kpEntityFactory;

    @Inject
    private ListFactory<KPEntity> kpEntityListFactory;
    
    @Inject
    private KPValueEntityMapper kpValueEntityMapper;

    @Override
    public List<KPEntity> map(List<KP> kpList) {
        throwIfNull(kpList, "kpList");

        List<KPEntity> kpel = kpEntityListFactory.create();
        kpList.forEach(kp -> kpel.add(map(kp)));

        return kpel;
    }

    private KPEntity map(KP kp) {
        KPEntity kpe = kpEntityFactory.create();

        if (kp.getTimeTag() == null) {
            throw new RuntimeException("Mapping KP to KPEntity: Timetag will a null content.");
        }
        
        List<KPValueEntity> kpvel = kpValueEntityMapper.map(kp.getKPValueList(), kpe);
        kpe.setKPValueList(kpvel);

        kpe.setTimeTag(kp.getTimeTag());
        kpe.setAp(kp.getAp());
        kpe.setCp(kp.getCp());
        kpe.setMostDisturbedAndQuietDays(kp.getMostDisturbedAndQuietDays());
        kpe.setSum(kp.getSum());
        kpe.setSumFlag(kp.getSumFlag());

        return kpe;

    }

}
