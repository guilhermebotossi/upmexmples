package br.inpe.climaespacial.swd.values.rmp.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.rmp.average.dtos.AverageRMP;
import br.inpe.climaespacial.swd.values.rmp.average.entities.AverageRMPEntity;
import br.inpe.climaespacial.swd.values.rmp.average.factories.AverageRMPFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultAverageRMPMapper implements AverageRMPMapper {

    @Inject
    private ListFactory<AverageRMP> averageRMPListFactory;

    @Inject
    private AverageRMPFactory averageRMPFactory;

    @Override
    public List<AverageRMP> map(List<AverageRMPEntity> averageRMPEntityList) {
        throwIfNull(averageRMPEntityList, "averageRMPEntityList");

        List<AverageRMP> averageRMPl = averageRMPListFactory.create();

        averageRMPEntityList.forEach(averageRMPe -> averageRMPl.add(convertToAverageRMP(averageRMPe)));
        return averageRMPl;
    }

    private AverageRMP convertToAverageRMP(AverageRMPEntity averageRMPe) {
        return averageRMPFactory.create(averageRMPe.getTimeTag(), averageRMPe.getAverageRMP());
    }

}
