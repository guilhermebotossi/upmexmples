package br.inpe.climaespacial.swd.values.by.value.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.by.value.dtos.BY;
import br.inpe.climaespacial.swd.values.by.value.entities.BYEntity;

public interface BYMapper {

	List<BY> map(List<BYEntity> byEntityList);

}
