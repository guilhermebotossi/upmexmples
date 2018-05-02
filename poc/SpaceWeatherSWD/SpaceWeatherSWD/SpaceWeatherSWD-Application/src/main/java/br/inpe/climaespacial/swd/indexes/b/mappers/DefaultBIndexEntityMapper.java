package br.inpe.climaespacial.swd.indexes.b.mappers;

import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import br.inpe.climaespacial.swd.indexes.b.factories.BIndexEntityFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultBIndexEntityMapper implements BIndexEntityMapper {

    @Inject
    private BIndexEntityFactory bIndexEntityFactory;

    @Override
    public BIndexEntity map(BIndex bIndex) {

        throwIfNull(bIndex, "bIndex");

        BIndexEntity bie = bIndexEntityFactory.create();
        bie.setTimeTag(bIndex.getTimeTag());
        bie.setPostValue(bIndex.getPostValue());
        bie.setPreValue(bIndex.getPreValue());

        return bie;
    }

}
