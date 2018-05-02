package br.inpe.climaespacial.swd.values.bz.average.mappers;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.bz.average.dtos.AverageBZ;
import br.inpe.climaespacial.swd.values.bz.average.entities.AverageBZEntity;
import br.inpe.climaespacial.swd.values.bz.average.factories.AverageBZFactory;

@Dependent
public class DefaultAverageBZMapper implements AverageBZMapper {
	
	@Inject
	private ListFactory<AverageBZ> averageBZListFactory;
	
	@Inject
	private AverageBZFactory averageBZFactory;

	@Override
	public List<AverageBZ> map(List<AverageBZEntity> averageBZEntityList) {
		if(averageBZEntityList == null) {
			throw new RuntimeException("Parâmetro \"averageBZEntityList\" null.");
		}
		
		List<AverageBZ> averageBZl = averageBZListFactory.create();
		
		averageBZEntityList.forEach(averageBZe ->  averageBZl.add(convertToAverageBZ(averageBZe)));
		return averageBZl;
	}

	private AverageBZ convertToAverageBZ(AverageBZEntity averageBZe) {
		return averageBZFactory.create(averageBZe.getTimeTag(), averageBZe.getAverageBZ());
	}

}
