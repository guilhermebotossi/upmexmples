package br.inpe.climaespacial.swd.values.density.value.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.density.value.dtos.Density;
import br.inpe.climaespacial.swd.values.density.value.entities.DensityEntity;

public interface DensityMapper {

	List<Density> map(List<DensityEntity> densityEntityList);

}
