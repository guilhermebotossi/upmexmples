package br.inpe.climaespacial.swd.values.ey.value.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.ey.value.dtos.EY;
import br.inpe.climaespacial.swd.values.ey.value.entities.EYEntity;

public interface EYMapper {

	List<EY> map(List<EYEntity> eyEntityList);

}
