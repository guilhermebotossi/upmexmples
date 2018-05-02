package br.inpe.climaespacial.swd.values.ey.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.ey.average.dtos.AverageEY;
import br.inpe.climaespacial.swd.values.ey.average.entities.AverageEYEntity;
import br.inpe.climaespacial.swd.values.ey.average.factories.AverageEYFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultAverageEYMapper implements AverageEYMapper {

    @Inject
    private ListFactory<AverageEY> averageEYListFactory;

    @Inject
    private AverageEYFactory averageEYFactory;

    @Override
    public List<AverageEY> map(List<AverageEYEntity> averageEYEntityList) {
        throwIfNull(averageEYEntityList, "averageEYEntityList");

        List<AverageEY> averageEYl = averageEYListFactory.create();

        averageEYEntityList.forEach(averageEYe -> averageEYl.add(convertToAverageEY(averageEYe)));
        return averageEYl;
    }

    private AverageEY convertToAverageEY(AverageEYEntity averageEYe) {
        return averageEYFactory.create(averageEYe.getTimeTag(), averageEYe.getAverageEY());
    }

}
