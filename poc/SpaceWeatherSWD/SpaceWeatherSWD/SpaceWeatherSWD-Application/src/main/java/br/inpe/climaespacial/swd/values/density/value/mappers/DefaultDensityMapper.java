package br.inpe.climaespacial.swd.values.density.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.density.value.dtos.Density;
import br.inpe.climaespacial.swd.values.density.value.entities.DensityEntity;
import br.inpe.climaespacial.swd.values.density.value.factories.DensityFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultDensityMapper implements DensityMapper {

    @Inject
    private ListFactory<Density> densityListFactory;

    @Inject
    private DensityFactory densityFactory;

    @Override
    public List<Density> map(List<DensityEntity> densityEntityList) {
        throwIfNull(densityEntityList, "densityEntityList");

        List<Density> densityl = densityListFactory.create();

        densityEntityList.forEach(densitye -> densityl.add(convertToDensity(densitye)));
        return densityl;
    }

    private Density convertToDensity(DensityEntity densitye) {
        return densityFactory.create(densitye.getTimeTag(), densitye.getDensity());
    }

}
