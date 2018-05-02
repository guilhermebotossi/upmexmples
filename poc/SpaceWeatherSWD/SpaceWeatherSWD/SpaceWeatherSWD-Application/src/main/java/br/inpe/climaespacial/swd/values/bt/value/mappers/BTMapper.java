package br.inpe.climaespacial.swd.values.bt.value.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.bt.value.dtos.BT;
import br.inpe.climaespacial.swd.values.bt.value.entities.BTEntity;

public interface BTMapper {

	List<BT> map(List<BTEntity> btEntityList);

}
