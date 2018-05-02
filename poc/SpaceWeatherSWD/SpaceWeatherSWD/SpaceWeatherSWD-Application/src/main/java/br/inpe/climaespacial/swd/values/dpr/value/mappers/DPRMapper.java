package br.inpe.climaespacial.swd.values.dpr.value.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.dpr.value.dtos.DPR;
import br.inpe.climaespacial.swd.values.dpr.value.entities.DPREntity;

public interface DPRMapper {

	List<DPR> map(List<DPREntity> dprEntityList);

}
