package br.inpe.climaespacial.swd.indexes.z.mappers;

import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;

public interface ZIndexEntityMapper {

	ZIndexEntity map(ZIndex zIndex);

}
