package br.inpe.climaespacial.swd.values.dpr.value.mappers;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.dpr.value.dtos.DPR;
import br.inpe.climaespacial.swd.values.dpr.value.entities.DPREntity;
import br.inpe.climaespacial.swd.values.dpr.value.factories.DPRFactory;

@Dependent
public class DefaultDPRMapper implements DPRMapper {
	
	@Inject
	private ListFactory<DPR> dprListFactory;
	
	@Inject
	private DPRFactory dprFactory;

	@Override
	public List<DPR> map(List<DPREntity> dprEntityList) {
		if(dprEntityList == null) {
			throw new RuntimeException("Parâmetro \"dprEntityList\" null.");
		}
		
		List<DPR> dprl = dprListFactory.create();
		
		dprEntityList.forEach(dpre ->  dprl.add(convertToDPR(dpre)));
		return dprl;
	}

	private DPR convertToDPR(DPREntity dpre) {
		return dprFactory.create(dpre.getTimeTag(), dpre.getDPR());
	}

}
