package br.inpe.climaespacial.swd.values.bx.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.bx.value.dtos.BX;
import br.inpe.climaespacial.swd.values.bx.value.entities.BXEntity;
import br.inpe.climaespacial.swd.values.bx.value.factories.BXFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultBXMapper implements BXMapper {

    @Inject
    private ListFactory<BX> bxListFactory;

    @Inject
    private BXFactory bxFactory;

    @Override
    public List<BX> map(List<BXEntity> bxEntityList) {
        throwIfNull(bxEntityList, "bxEntityList");

        List<BX> bxl = bxListFactory.create();

        bxEntityList.forEach(bxe -> bxl.add(convertToBX(bxe)));
        return bxl;
    }

    private BX convertToBX(BXEntity bxe) {
        return bxFactory.create(bxe.getTimeTag(), bxe.getBX());
    }

}
