package br.inpe.climaespacial.swd.values.density.average.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.density.average.dtos.AverageDensity;
import br.inpe.climaespacial.swd.values.density.average.entities.AverageDensityEntity;

public interface AverageDensityMapper {

	List<AverageDensity> map(List<AverageDensityEntity> averageDensityEntityList);

}
