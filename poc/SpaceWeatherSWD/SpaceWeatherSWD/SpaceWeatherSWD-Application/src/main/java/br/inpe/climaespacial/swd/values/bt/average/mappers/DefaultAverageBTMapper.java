package br.inpe.climaespacial.swd.values.bt.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.bt.average.dtos.AverageBT;
import br.inpe.climaespacial.swd.values.bt.average.entities.AverageBTEntity;
import br.inpe.climaespacial.swd.values.bt.average.factories.AverageBTFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultAverageBTMapper implements AverageBTMapper {

    @Inject
    private ListFactory<AverageBT> averageBTListFactory;

    @Inject
    private AverageBTFactory averageBTFactory;

    @Override
    public List<AverageBT> map(List<AverageBTEntity> averageBTEntityList) {
        throwIfNull(averageBTEntityList, "averageBTEntityList");

        List<AverageBT> averageBTl = averageBTListFactory.create();

        averageBTEntityList.forEach(averageBTe -> averageBTl.add(convertToAverageBT(averageBTe)));
        return averageBTl;
    }

    private AverageBT convertToAverageBT(AverageBTEntity averageBTe) {
        return averageBTFactory.create(averageBTe.getTimeTag(), averageBTe.getAverageBT());
    }

}
