package br.inpe.climaespacial.swd.values.bx.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.bx.average.dtos.AverageBX;
import br.inpe.climaespacial.swd.values.bx.average.entities.AverageBXEntity;
import br.inpe.climaespacial.swd.values.bx.average.factories.AverageBXFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultAverageBXMapper implements AverageBXMapper {

    @Inject
    private ListFactory<AverageBX> averageBXListFactory;

    @Inject
    private AverageBXFactory averageBXFactory;

    @Override
    public List<AverageBX> map(List<AverageBXEntity> averageBXEntityList) {
        throwIfNull(averageBXEntityList, "averageBXEntityList");

        List<AverageBX> averageBXl = averageBXListFactory.create();

        averageBXEntityList.forEach(averageBXe -> averageBXl.add(convertToAverageBX(averageBXe)));
        return averageBXl;
    }

    private AverageBX convertToAverageBX(AverageBXEntity averageBXe) {
        return averageBXFactory.create(averageBXe.getTimeTag(), averageBXe.getAverageBX());
    }

}
