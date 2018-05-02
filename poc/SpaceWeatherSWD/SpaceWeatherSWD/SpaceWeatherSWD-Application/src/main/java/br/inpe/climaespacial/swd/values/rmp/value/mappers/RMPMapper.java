package br.inpe.climaespacial.swd.values.rmp.value.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;
import br.inpe.climaespacial.swd.values.rmp.value.entities.RMPEntity;

public interface RMPMapper {

	List<RMP> map(List<RMPEntity> rmpEntityList);

}
