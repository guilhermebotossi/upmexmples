package br.inpe.climaespacial.swd.values.by.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.by.average.dtos.AverageBY;
import br.inpe.climaespacial.swd.values.by.average.entities.AverageBYEntity;
import br.inpe.climaespacial.swd.values.by.average.factories.AverageBYFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultAverageBYMapper implements AverageBYMapper {

    @Inject
    private ListFactory<AverageBY> averageBYListFactory;

    @Inject
    private AverageBYFactory averageBYFactory;

    @Override
    public List<AverageBY> map(List<AverageBYEntity> averageBYEntityList) {
        throwIfNull(averageBYEntityList, "averageBYEntityList");

        List<AverageBY> averageBYl = averageBYListFactory.create();

        averageBYEntityList.forEach(averageBYe -> averageBYl.add(convertToAverageBY(averageBYe)));
        return averageBYl;
    }

    private AverageBY convertToAverageBY(AverageBYEntity averageBYe) {
        return averageBYFactory.create(averageBYe.getTimeTag(), averageBYe.getAverageBY());
    }

}
