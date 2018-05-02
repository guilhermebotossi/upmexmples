package br.inpe.climaespacial.swd.acquisition.mappers;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.factories.MagEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultMagEntityMapper implements MagEntityMapper {

    @Inject
    private ListFactory<MagEntity> magEntityListFactory;

    @Inject
    private MagEntityFactory magEntityFactory;

    @Override
    public List<MagEntity> map(List<Mag> magList) {

        throwIfNull(magList, "magList");

        List<MagEntity> mel = magEntityListFactory.create();

        for (Mag m : magList) {
            MagEntity me = mapToEntity(m);
            mel.add(me);
        }

        return mel;
    }

    private MagEntity mapToEntity(Mag m) {
        MagEntity me = magEntityFactory.create();
        me.setTimeTag(m.getTimeTag());
        me.setBt(m.getBt());
        me.setBxGsm(m.getBxGsm());
        me.setByGsm(m.getByGsm());
        me.setBzGsm(m.getBzGsm());
        me.setLatGsm(m.getLatGsm());
        me.setLonGsm(m.getLonGsm());
        return me;
    }
}
