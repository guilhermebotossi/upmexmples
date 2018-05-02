package br.inpe.climaespacial.swd.values.temperature.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.temperature.value.dtos.Temperature;
import br.inpe.climaespacial.swd.values.temperature.value.entities.TemperatureEntity;
import br.inpe.climaespacial.swd.values.temperature.value.factories.TemperatureFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultTemperatureMapper implements TemperatureMapper {

    @Inject
    private ListFactory<Temperature> temperatureListFactory;

    @Inject
    private TemperatureFactory temperatureFactory;

    @Override
    public List<Temperature> map(List<TemperatureEntity> temperatureEntityList) {
        throwIfNull(temperatureEntityList, "temperatureEntityList");

        List<Temperature> temperaturel = temperatureListFactory.create();

        temperatureEntityList.forEach(temperaturee -> temperaturel.add(convertToTemperature(temperaturee)));
        return temperaturel;
    }

    private Temperature convertToTemperature(TemperatureEntity temperaturee) {
        return temperatureFactory.create(temperaturee.getTimeTag(), temperaturee.getTemperature());
    }

}
