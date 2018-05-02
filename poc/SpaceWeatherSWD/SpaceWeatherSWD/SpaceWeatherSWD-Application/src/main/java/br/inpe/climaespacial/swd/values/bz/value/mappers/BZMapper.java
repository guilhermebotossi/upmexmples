package br.inpe.climaespacial.swd.values.bz.value.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.bz.value.dtos.BZ;
import br.inpe.climaespacial.swd.values.bz.value.entities.BZEntity;

public interface BZMapper {

	List<BZ> map(List<BZEntity> bzEntityList);

}
