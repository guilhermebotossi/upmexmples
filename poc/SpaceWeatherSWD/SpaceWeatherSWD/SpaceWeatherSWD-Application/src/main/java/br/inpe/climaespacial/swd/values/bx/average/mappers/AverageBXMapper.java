package br.inpe.climaespacial.swd.values.bx.average.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.bx.average.dtos.AverageBX;
import br.inpe.climaespacial.swd.values.bx.average.entities.AverageBXEntity;

public interface AverageBXMapper {

	List<AverageBX> map(List<AverageBXEntity> averageBXEntityList);

}
