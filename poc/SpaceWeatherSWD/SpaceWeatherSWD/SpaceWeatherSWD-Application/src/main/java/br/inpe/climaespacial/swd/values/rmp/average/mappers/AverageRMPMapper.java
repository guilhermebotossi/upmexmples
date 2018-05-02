package br.inpe.climaespacial.swd.values.rmp.average.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.rmp.average.dtos.AverageRMP;
import br.inpe.climaespacial.swd.values.rmp.average.entities.AverageRMPEntity;

public interface AverageRMPMapper {

	List<AverageRMP> map(List<AverageRMPEntity> averageRMPEntityList);

}
