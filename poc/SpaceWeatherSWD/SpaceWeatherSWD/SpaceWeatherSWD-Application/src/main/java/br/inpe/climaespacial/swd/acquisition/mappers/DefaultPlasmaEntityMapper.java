package br.inpe.climaespacial.swd.acquisition.mappers;

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.calculation.factories.PlasmaEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultPlasmaEntityMapper implements PlasmaEntityMapper {

    @Inject
    private ListFactory<PlasmaEntity> listFactory;

    @Inject
    private PlasmaEntityFactory plasmaEntityFactory;

    @Override
    public List<PlasmaEntity> map(List<Plasma> plasmaList) {

        throwIfNull(plasmaList, "plasmaList");

        List<PlasmaEntity> pel = listFactory.create();

        for (Plasma p : plasmaList) {
            PlasmaEntity pe = mapToEntity(p);
            pel.add(pe);
        }

        return pel;
    }

    private PlasmaEntity mapToEntity(Plasma p) {
        PlasmaEntity pe = plasmaEntityFactory.create();
        pe.setTimeTag(p.getTimeTag());
        pe.setDensity(p.getDensity());
        pe.setSpeed(p.getSpeed());
        pe.setTemperature(p.getTemperature());
        return pe;
    }

}
