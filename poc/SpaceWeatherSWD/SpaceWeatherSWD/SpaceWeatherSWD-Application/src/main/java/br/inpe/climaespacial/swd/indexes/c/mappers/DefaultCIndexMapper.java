package br.inpe.climaespacial.swd.indexes.c.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.factories.CIndexFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultCIndexMapper implements CIndexMapper {

    @Inject
    private ListFactory<CIndex> cIndexListFactory;

    @Inject
    private CIndexFactory cIndexFactory;

    @Override
    public List<CIndex> map(List<CIndexEntity> cIndexEntityList) {

        throwIfNull(cIndexEntityList, "cIndexEntityList");

        List<CIndex> cil = cIndexListFactory.create();

        cIndexEntityList.forEach(cie -> {
            CIndex ci = cIndexFactory.create();
            ci.setTimeTag(cie.getTimeTag());
            ci.setPreValue(cie.getPreValue());
            ci.setPostValue(cie.getPostValue());
            cil.add(ci);
        });

        return cil;
    }

}
