package br.inpe.climaespacial.swd.values.speed.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.speed.value.dtos.Speed;
import br.inpe.climaespacial.swd.values.speed.value.entities.SpeedEntity;
import br.inpe.climaespacial.swd.values.speed.value.factories.SpeedFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultSpeedMapper implements SpeedMapper {

    @Inject
    private ListFactory<Speed> speedListFactory;

    @Inject
    private SpeedFactory speedFactory;

    @Override
    public List<Speed> map(List<SpeedEntity> speedEntityList) {
        throwIfNull(speedEntityList, "speedEntityList");

        List<Speed> speedl = speedListFactory.create();

        speedEntityList.forEach(speede -> speedl.add(convertToSpeed(speede)));
        return speedl;
    }

    private Speed convertToSpeed(SpeedEntity speede) {
        return speedFactory.create(speede.getTimeTag(), speede.getSpeed());
    }

}
