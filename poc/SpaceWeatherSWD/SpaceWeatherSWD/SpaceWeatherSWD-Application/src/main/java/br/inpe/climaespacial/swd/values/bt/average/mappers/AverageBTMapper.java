package br.inpe.climaespacial.swd.values.bt.average.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.bt.average.dtos.AverageBT;
import br.inpe.climaespacial.swd.values.bt.average.entities.AverageBTEntity;

public interface AverageBTMapper {

	List<AverageBT> map(List<AverageBTEntity> averageBTEntityList);

}
