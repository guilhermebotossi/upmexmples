package br.inpe.climaespacial.swd.values.temperature.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.temperature.average.dtos.AverageTemperature;
import br.inpe.climaespacial.swd.values.temperature.average.entities.AverageTemperatureEntity;
import br.inpe.climaespacial.swd.values.temperature.average.factories.AverageTemperatureFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultAverageTemperatureMapper implements AverageTemperatureMapper {

    @Inject
    private ListFactory<AverageTemperature> AverageTemperatureListFactory;

    @Inject
    private AverageTemperatureFactory AverageTemperatureFactory;

    @Override
    public List<AverageTemperature> map(List<AverageTemperatureEntity> averageTemperatureEntityList) {
        throwIfNull(averageTemperatureEntityList, "averageTemperatureEntityList");

        List<AverageTemperature> AverageTemperaturel = AverageTemperatureListFactory.create();

        averageTemperatureEntityList.forEach(AverageTemperaturee -> AverageTemperaturel.add(convertToAverageTemperature(AverageTemperaturee)));

        return AverageTemperaturel;
    }

    private AverageTemperature convertToAverageTemperature(AverageTemperatureEntity AverageTemperaturee) {
        return AverageTemperatureFactory.create(AverageTemperaturee.getTimeTag(), AverageTemperaturee.getAverageTemperature());
    }

}
