package br.inpe.climaespacial.swd.indexes.b.mappers;

import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;

public interface BIndexEntityMapper {

	BIndexEntity map(BIndex bIndex);

}
