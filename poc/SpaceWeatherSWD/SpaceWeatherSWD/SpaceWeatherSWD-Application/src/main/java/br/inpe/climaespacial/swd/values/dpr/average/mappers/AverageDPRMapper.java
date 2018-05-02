package br.inpe.climaespacial.swd.values.dpr.average.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;
import br.inpe.climaespacial.swd.values.dpr.average.entities.AverageDPREntity;

public interface AverageDPRMapper {

	List<AverageDPR> map(List<AverageDPREntity> averageDPREntityList);

}
