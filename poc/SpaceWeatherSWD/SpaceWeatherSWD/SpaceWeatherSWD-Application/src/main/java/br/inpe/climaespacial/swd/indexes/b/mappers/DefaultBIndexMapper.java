package br.inpe.climaespacial.swd.indexes.b.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import br.inpe.climaespacial.swd.indexes.b.factories.BIndexFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultBIndexMapper implements BIndexMapper {

    @Inject
    private ListFactory<BIndex> bIndexListFactory;

    @Inject
    private BIndexFactory bIndexFactory;

    @Override
    public List<BIndex> map(List<BIndexEntity> bIndexEntityList) {

        throwIfNull(bIndexEntityList, "bIndexEntityList");

        List<BIndex> bil = bIndexListFactory.create();

        bIndexEntityList.forEach(bie -> {
            BIndex bi = bIndexFactory.create();
            bi.setTimeTag(bie.getTimeTag());
            bi.setPreValue(bie.getPreValue());
            bi.setPostValue(bie.getPostValue());
            bil.add(bi);
        });

        return bil;
    }

}
