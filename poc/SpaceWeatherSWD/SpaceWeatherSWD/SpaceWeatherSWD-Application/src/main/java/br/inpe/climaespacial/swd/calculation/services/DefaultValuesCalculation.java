package br.inpe.climaespacial.swd.calculation.services;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import br.inpe.climaespacial.swd.calculation.dtos.CalculatedValues;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;
import br.inpe.climaespacial.swd.calculation.factories.CalculatedValuesFactory;
import br.inpe.climaespacial.swd.calculation.repositories.CalculatedValuesWriterRepository;
import br.inpe.climaespacial.swd.calculation.repositories.MagPlasmaReaderRepository;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;

@Dependent
public class DefaultValuesCalculation implements ValuesCalculation {

	@Inject
	private MagPlasmaReaderRepository magPlasmaReaderRepository;

	@Inject
	private CalculatedValuesWriterRepository calculatedValuesWriterRepository;

	@Inject
	private CalculatedValuesFactory calculatedValuesFactory;

	@Inject
	private ListFactory<CalculatedValues> calculatedValuesListFactory;

	@Inject
	private EyValueCalculator eyValueCalculator;

	@Inject
	private DprValueCalculator dprValueCalculator;

	@Inject
	private RmpValueCalculator rmpValueCalculator;

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public void calculate() {
		List<MagPlasma> mpl = null;
		
		while(!(mpl = magPlasmaReaderRepository.list()).isEmpty()){
		    List<CalculatedValues> cvl = calculatedValuesListFactory.create();

    		for (MagPlasma mp : mpl) {
    			Double ey = eyValueCalculator.calculate(mp);
    			Double dpr = dprValueCalculator.calculate(mp);
    			Double rmp = rmpValueCalculator.calculate(mp, dpr);
    			CalculatedValues cv = calculatedValuesFactory.create(ey, dpr, rmp, mp.getTimeTag());
    			cvl.add(cv);
    		}
    
    		calculatedValuesWriterRepository.save(cvl);
		}
	}
}
