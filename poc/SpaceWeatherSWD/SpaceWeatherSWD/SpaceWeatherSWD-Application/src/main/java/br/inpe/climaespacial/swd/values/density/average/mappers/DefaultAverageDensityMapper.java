package br.inpe.climaespacial.swd.values.density.average.mappers;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.density.average.dtos.AverageDensity;
import br.inpe.climaespacial.swd.values.density.average.entities.AverageDensityEntity;
import br.inpe.climaespacial.swd.values.density.average.factories.AverageDensityFactory;

@Dependent
public class DefaultAverageDensityMapper implements AverageDensityMapper {
	
	@Inject
	private ListFactory<AverageDensity> averageDensityListFactory;
	
	@Inject
	private AverageDensityFactory averageDensityFactory;

	@Override
	public List<AverageDensity> map(List<AverageDensityEntity> averageDensityEntityList) {
		if(averageDensityEntityList == null) {
			throw new RuntimeException("Parâmetro \"averageDensityEntityList\" null.");
		}
		
		List<AverageDensity> averageDensityl = averageDensityListFactory.create();
		
		averageDensityEntityList.forEach(averageDensitye ->  averageDensityl.add(convertToAverageDensity(averageDensitye)));
		return averageDensityl;
	}

	private AverageDensity convertToAverageDensity(AverageDensityEntity averageDensitye) {
		return averageDensityFactory.create(averageDensitye.getTimeTag(), averageDensitye.getAverageDensity());
	}

}
