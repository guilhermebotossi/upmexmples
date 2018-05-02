package br.inpe.climaespacial.swd.values.by.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.by.value.dtos.BY;
import br.inpe.climaespacial.swd.values.by.value.entities.BYEntity;
import br.inpe.climaespacial.swd.values.by.value.factories.BYFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultBYMapper implements BYMapper {

    @Inject
    private ListFactory<BY> byListFactory;

    @Inject
    private BYFactory byFactory;

    @Override
    public List<BY> map(List<BYEntity> byEntityList) {
        throwIfNull(byEntityList, "byEntityList");

        List<BY> byl = byListFactory.create();

        byEntityList.forEach(bye -> byl.add(convertToBY(bye)));
        return byl;
    }

    private BY convertToBY(BYEntity bye) {
        return byFactory.create(bye.getTimeTag(), bye.getBY());
    }

}
