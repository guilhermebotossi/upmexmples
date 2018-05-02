package br.inpe.climaespacial.swd.values.by.average.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.by.average.dtos.AverageBY;
import br.inpe.climaespacial.swd.values.by.average.entities.AverageBYEntity;

public interface AverageBYMapper {

	List<AverageBY> map(List<AverageBYEntity> averageBYEntityList);

}
