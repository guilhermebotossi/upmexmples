package br.inpe.climaespacial.swd.average;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.repositories.HourlyAverageWriterRepository;
import br.inpe.climaespacial.swd.average.repositories.MagPlasmaCalculatedReaderRepository;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;
import br.inpe.climaespacial.swd.commons.EmbraceMockito;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultAverage.class)
public class AverageTest {
	
	@Produces @Mock
	private MagPlasmaCalculatedReaderRepository nextRecordRepository;
	
	@Produces @Mock
	private HourlyAverageCalculator hourlyAverageCalculator;
	
	@Produces @Mock
	private HourlyAverageWriterRepository hourlyAverageRepository;
	
	@Inject	
	private Average average;
	
	@Test(timeout = 1000)
	public void calculate_called_persistsHourlyAverage() {
		List<MagPlasmaCalculated> mpcl = mockList(MagPlasmaCalculated.class);
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>>  se = EmbraceMockito.mockSimpleEntryWithList(ZonedDateTime.class, MagPlasmaCalculated.class);
		SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>>  se2 = EmbraceMockito.mockSimpleEntryWithList(ZonedDateTime.class, MagPlasmaCalculated.class);
		when(nextRecordRepository.list()).thenReturn(se).thenReturn(se2);
		when(se.getKey()).thenReturn(zdt);
		when(se2.getKey()).thenReturn(null);
		when(se.getValue()).thenReturn(mpcl);
		HourlyAverage hourlyAverage = mock(HourlyAverage.class);
		when(hourlyAverageCalculator.calculate(zdt, mpcl)).thenReturn(hourlyAverage);
		when(hourlyAverage.getTimeTag()).thenReturn(zdt);
		
		average.calculate();
		 
		verify(nextRecordRepository, times(2)).list();
		verify(hourlyAverageCalculator, times(1)).calculate(zdt, mpcl);
		verify(hourlyAverageRepository, times(1)).save(hourlyAverage);
	}
	
	@Test(timeout = 1000)
	public void calculate_called_persists2HourlyAverage() {
		List<MagPlasmaCalculated> mpcl1 = mockList(MagPlasmaCalculated.class);
		List<MagPlasmaCalculated> mpcl2 = mockList(MagPlasmaCalculated.class);
		ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
		SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>>  se1 = EmbraceMockito.mockSimpleEntryWithList(ZonedDateTime.class, MagPlasmaCalculated.class);
		SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>>  se2 = EmbraceMockito.mockSimpleEntryWithList(ZonedDateTime.class, MagPlasmaCalculated.class);
		SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>>  se3 = EmbraceMockito.mockSimpleEntryWithList(ZonedDateTime.class, MagPlasmaCalculated.class);
		when(nextRecordRepository.list()).thenReturn(se1)
		.thenReturn(se2)
		.thenReturn(se3);
		when(se1.getKey()).thenReturn(zdt1);
		when(se1.getValue()).thenReturn(mpcl1);
		
		when(se2.getKey()).thenReturn(zdt2);
		when(se2.getValue()).thenReturn(mpcl2);
		
		when(se3.getKey()).thenReturn(null);
		
		HourlyAverage hourlyAverage1 = mock(HourlyAverage.class);
		HourlyAverage hourlyAverage2 = mock(HourlyAverage.class);
		
		when(hourlyAverageCalculator.calculate(zdt1, mpcl1)).thenReturn(hourlyAverage1);
		when(hourlyAverage1.getTimeTag()).thenReturn(zdt1);
		
		when(hourlyAverageCalculator.calculate(zdt2, mpcl2)).thenReturn(hourlyAverage2);
		when(hourlyAverage1.getTimeTag()).thenReturn(zdt2);
		
		average.calculate();
		 
		verify(nextRecordRepository, times(3)).list();
		verify(hourlyAverageCalculator, times(1)).calculate(zdt1, mpcl1);
		verify(hourlyAverageCalculator, times(1)).calculate(zdt2, mpcl2);
		verify(hourlyAverageRepository, times(1)).save(hourlyAverage1);
		verify(hourlyAverageRepository, times(1)).save(hourlyAverage2);
	}

	@Test(timeout = 1000)
	public void calculate_called_doNothing() {
		SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>>  se = EmbraceMockito.mockSimpleEntryWithList(ZonedDateTime.class, MagPlasmaCalculated.class);
		when(nextRecordRepository.list()).thenReturn(se);
		when(se.getKey()).thenReturn(null);
		
		average.calculate();
		 
		verify(nextRecordRepository, times(1)).list();
		verify(hourlyAverageCalculator, never()).calculate(any(), any()); 
	}
}
