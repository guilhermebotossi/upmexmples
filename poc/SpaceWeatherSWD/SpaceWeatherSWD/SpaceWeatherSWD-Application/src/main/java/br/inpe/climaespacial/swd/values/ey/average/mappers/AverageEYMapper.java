package br.inpe.climaespacial.swd.values.ey.average.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.ey.average.dtos.AverageEY;
import br.inpe.climaespacial.swd.values.ey.average.entities.AverageEYEntity;

public interface AverageEYMapper {

	List<AverageEY> map(List<AverageEYEntity> averageEYEntityList);

}
