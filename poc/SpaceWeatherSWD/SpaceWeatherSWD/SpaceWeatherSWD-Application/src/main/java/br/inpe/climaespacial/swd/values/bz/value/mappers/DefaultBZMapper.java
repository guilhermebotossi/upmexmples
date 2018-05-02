package br.inpe.climaespacial.swd.values.bz.value.mappers;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.bz.value.dtos.BZ;
import br.inpe.climaespacial.swd.values.bz.value.entities.BZEntity;
import br.inpe.climaespacial.swd.values.bz.value.factories.BZFactory;

@Dependent
public class DefaultBZMapper implements BZMapper {
	
	@Inject
	private ListFactory<BZ> bzListFactory;
	
	@Inject
	private BZFactory bzFactory;

	@Override
	public List<BZ> map(List<BZEntity> bzEntityList) {
		if(bzEntityList == null) {
			throw new RuntimeException("Parâmetro \"bzEntityList\" null.");
		}
		
		List<BZ> bzl = bzListFactory.create();
		
		bzEntityList.forEach(bze ->  bzl.add(convertToBZ(bze)));
		return bzl;
	}

	private BZ convertToBZ(BZEntity bze) {
		return bzFactory.create(bze.getTimeTag(), bze.getBZ());
	}

}
