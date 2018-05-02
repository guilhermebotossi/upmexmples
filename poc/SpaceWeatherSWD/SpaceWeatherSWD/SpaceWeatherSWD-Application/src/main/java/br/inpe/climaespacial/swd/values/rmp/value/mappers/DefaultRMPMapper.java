package br.inpe.climaespacial.swd.values.rmp.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;
import br.inpe.climaespacial.swd.values.rmp.value.entities.RMPEntity;
import br.inpe.climaespacial.swd.values.rmp.value.factories.RMPFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultRMPMapper implements RMPMapper {

    @Inject
    private ListFactory<RMP> rmpListFactory;

    @Inject
    private RMPFactory rmpFactory;

    @Override
    public List<RMP> map(List<RMPEntity> rmpEntityList) {
        throwIfNull(rmpEntityList, "rmpEntityList");

        List<RMP> rmpl = rmpListFactory.create();

        rmpEntityList.forEach(rmpe -> rmpl.add(convertToRMP(rmpe)));
        return rmpl;
    }

    private RMP convertToRMP(RMPEntity rmpe) {
        return rmpFactory.create(rmpe.getTimeTag(), rmpe.getRMP());
    }

}
