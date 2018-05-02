package br.inpe.climaespacial.swd.values.dpr.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;
import br.inpe.climaespacial.swd.values.dpr.average.entities.AverageDPREntity;
import br.inpe.climaespacial.swd.values.dpr.average.factories.AverageDPRFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultAverageDPRMapper implements AverageDPRMapper {

    @Inject
    private ListFactory<AverageDPR> averageDPRListFactory;

    @Inject
    private AverageDPRFactory averageDPRFactory;

    @Override
    public List<AverageDPR> map(List<AverageDPREntity> averageDPREntityList) {
        throwIfNull(averageDPREntityList, "averageDPREntityList");

        List<AverageDPR> averageDPRl = averageDPRListFactory.create();

        averageDPREntityList.forEach(averageDPRe -> averageDPRl.add(convertToAverageDPR(averageDPRe)));
        return averageDPRl;
    }

    private AverageDPR convertToAverageDPR(AverageDPREntity averageDPRe) {
        return averageDPRFactory.create(averageDPRe.getTimeTag(), averageDPRe.getAverageDPR());
    }

}
