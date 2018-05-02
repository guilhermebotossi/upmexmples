package br.inpe.climaespacial.swd.values.bx.value.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.bx.value.dtos.BX;
import br.inpe.climaespacial.swd.values.bx.value.entities.BXEntity;

public interface BXMapper {

	List<BX> map(List<BXEntity> bxEntityList);

}
