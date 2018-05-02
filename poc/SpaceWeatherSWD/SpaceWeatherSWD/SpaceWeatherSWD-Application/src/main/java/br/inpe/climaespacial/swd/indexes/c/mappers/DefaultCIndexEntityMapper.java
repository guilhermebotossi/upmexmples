package br.inpe.climaespacial.swd.indexes.c.mappers;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.factories.CIndexEntityFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultCIndexEntityMapper implements CIndexEntityMapper {

    @Inject
    private CIndexEntityFactory cIndexEntityFactory;

    @Override
    public CIndexEntity map(CIndex cIndex) {

        throwIfNull(cIndex, "cIndex");

        CIndexEntity cie = cIndexEntityFactory.create();
        cie.setTimeTag(cIndex.getTimeTag());
        cie.setPreValue(cIndex.getPreValue());
        cie.setPostValue(cIndex.getPostValue());

        return cie;
    }

}
