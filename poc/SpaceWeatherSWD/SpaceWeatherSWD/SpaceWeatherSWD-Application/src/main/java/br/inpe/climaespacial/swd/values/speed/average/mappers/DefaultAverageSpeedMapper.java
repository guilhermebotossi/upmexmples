package br.inpe.climaespacial.swd.values.speed.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.speed.average.dtos.AverageSpeed;
import br.inpe.climaespacial.swd.values.speed.average.entities.AverageSpeedEntity;
import br.inpe.climaespacial.swd.values.speed.average.factories.AverageSpeedFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultAverageSpeedMapper implements AverageSpeedMapper {

    @Inject
    private ListFactory<AverageSpeed> AverageSpeedListFactory;

    @Inject
    private AverageSpeedFactory AverageSpeedFactory;

    @Override
    public List<AverageSpeed> map(List<AverageSpeedEntity> averageSpeedEntityList) {
        throwIfNull(averageSpeedEntityList, "averageSpeedEntityList");

        List<AverageSpeed> AverageSpeedl = AverageSpeedListFactory.create();

        averageSpeedEntityList.forEach(AverageSpeede -> AverageSpeedl.add(convertToAverageSpeed(AverageSpeede)));
        return AverageSpeedl;
    }

    private AverageSpeed convertToAverageSpeed(AverageSpeedEntity AverageSpeede) {
        return AverageSpeedFactory.create(AverageSpeede.getTimeTag(), AverageSpeede.getAverageSpeed());
    }

}
