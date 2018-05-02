package br.inpe.climaespacial.swd.indexes.z.mappers;

import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import br.inpe.climaespacial.swd.indexes.z.factories.ZIndexEntityFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultZIndexEntityMapper implements ZIndexEntityMapper {

    @Inject
    private ZIndexEntityFactory zIndexEntityFactory;

    @Override
    public ZIndexEntity map(ZIndex zIndex) {

        throwIfNull(zIndex, "zIndex");

        ZIndexEntity zie = zIndexEntityFactory.create();
        zie.setTimeTag(zIndex.getTimeTag());
        zie.setPreValue(zIndex.getPreValue());
        zie.setPostValue(zIndex.getPostValue());

        return zie;
    }

}
