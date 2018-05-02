package br.inpe.climaespacial.swd.calculation.services;

import br.inpe.climaespacial.swd.calculation.dtos.CalculatedValues;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;
import br.inpe.climaespacial.swd.calculation.factories.CalculatedValuesFactory;
import br.inpe.climaespacial.swd.calculation.repositories.CalculatedValuesWriterRepository;
import br.inpe.climaespacial.swd.calculation.repositories.MagPlasmaReaderRepository;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.refEq;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultValuesCalculation.class)
public class ValuesCalculationTest {

	@Produces
	@Mock
	private MagPlasmaReaderRepository magPlasmaReaderRepository;

	@Produces
	@Mock
	private CalculatedValuesWriterRepository calculatedValuesWriterRepository;

	@Produces
	@Mock
	private CalculatedValuesFactory calculatedValuesFactory;

	@Produces
	@Mock
	private ListFactory<CalculatedValues> calculatedValuesListFactory;

	@Produces
	@Mock
	private EyValueCalculator eyValueCalculator;

	@Produces
	@Mock
	private DprValueCalculator pprValueCalculator;

	@Produces
	@Mock
	private RmpValueCalculator rmpValueCalculator;

	@Inject
	private ValuesCalculation valuesCalculation;

	@Test
	public void calculate_called_succeeded() {

		MagPlasma mp1 = mock(MagPlasma.class);
		MagPlasma mp2 = mock(MagPlasma.class);
		List<MagPlasma> mpl1 = Arrays.asList(mp1, mp2);
		List<MagPlasma> mpl2 = mockList(MagPlasma.class);

		CalculatedValues cv1 = mock(CalculatedValues.class);
		CalculatedValues cv2 = mock(CalculatedValues.class);
		List<CalculatedValues> cvl = mockList(CalculatedValues.class);

		when(magPlasmaReaderRepository.list()).thenReturn(mpl1).thenReturn(mpl2);

		double eyCv1 = 1.0, dprCv1 = 2.0, rmpCv1 = 3.0, eyCv2 = 4.0,
				dprCv2 = 5.0, rmpCv2 = 6.0;
		
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		
		when(eyValueCalculator.calculate(mp1)).thenReturn(1.0);
		when(pprValueCalculator.calculate(mp1)).thenReturn(2.0);
		when(rmpValueCalculator.calculate(mp1, 2.0)).thenReturn(3.0);
		when(eyValueCalculator.calculate(mp2)).thenReturn(4.0);
		when(pprValueCalculator.calculate(mp2)).thenReturn(5.0);
		when(rmpValueCalculator.calculate(mp2, 5.0)).thenReturn(6.0);
		when(calculatedValuesFactory.create(eyCv1, dprCv1, rmpCv1, zdt)).thenReturn(cv1);
		when(calculatedValuesFactory.create(eyCv2, dprCv2, rmpCv2, zdt)).thenReturn(cv2);
		when(calculatedValuesListFactory.create()).thenReturn(cvl);
		when(mp1.getTimeTag()).thenReturn(zdt);
		when(mp2.getTimeTag()).thenReturn(zdt);
		when(mpl2.isEmpty()).thenReturn(true);

		valuesCalculation.calculate(); 

		verify(magPlasmaReaderRepository, times(2)).list();
		verify(eyValueCalculator, times(1)).calculate(mp1);
		verify(eyValueCalculator, times(1)).calculate(mp2);

		verify(pprValueCalculator, times(1)).calculate(mp1);
		verify(pprValueCalculator, times(1)).calculate(mp2);

		verify(rmpValueCalculator, times(1)).calculate(mp1, 2.0);
		verify(rmpValueCalculator, times(1)).calculate(mp2, 5.0);

		verify(calculatedValuesListFactory, times(1)).create();
		verify(calculatedValuesFactory, times(1)).create(eyCv1, dprCv1, rmpCv1, zdt);
		verify(calculatedValuesFactory, times(1)).create(eyCv2, dprCv2, rmpCv2, zdt);

		verify(calculatedValuesWriterRepository, times(1)).save(refEq(cvl));
	}
}

