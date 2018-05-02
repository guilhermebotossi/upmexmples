package br.inpe.climaespacial.swd.indexes.v.mappers;

import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import br.inpe.climaespacial.swd.indexes.v.factories.VIndexEntityFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultVIndexEntityMapper implements VIndexEntityMapper {

    @Inject
    private VIndexEntityFactory vIndexEntityFactory;

    @Override
    public VIndexEntity map(VIndex vIndex) {

        throwIfNull(vIndex, "vIndex");

        VIndexEntity vie = vIndexEntityFactory.create();
        vie.setTimeTag(vIndex.getTimeTag());
        vie.setPreValue(vIndex.getPreValue());
        vie.setPostValue(vIndex.getPostValue());
        vie.setIsCycleBegin(vIndex.getIsCycleBegin());

        return vie;
    }

}
