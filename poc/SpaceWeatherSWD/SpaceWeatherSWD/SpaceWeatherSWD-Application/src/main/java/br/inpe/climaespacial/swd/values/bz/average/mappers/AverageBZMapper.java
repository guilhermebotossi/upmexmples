package br.inpe.climaespacial.swd.values.bz.average.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.bz.average.dtos.AverageBZ;
import br.inpe.climaespacial.swd.values.bz.average.entities.AverageBZEntity;

public interface AverageBZMapper {

	List<AverageBZ> map(List<AverageBZEntity> averageBZEntityList);

}
