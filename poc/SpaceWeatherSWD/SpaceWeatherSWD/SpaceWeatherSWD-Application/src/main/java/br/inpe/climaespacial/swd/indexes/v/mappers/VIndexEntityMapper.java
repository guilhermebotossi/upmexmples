package br.inpe.climaespacial.swd.indexes.v.mappers;

import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;

public interface VIndexEntityMapper {

    VIndexEntity map(VIndex vIndex);

}
