package br.inpe.climaespacial.swd.average.providers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.average.DefaultNextHourProvider;
import br.inpe.climaespacial.swd.average.repositories.MagPlasmaCalculatedValuesNextHourRepository;
import br.inpe.climaespacial.swd.average.validators.NextHourValidator;
import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;


@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultNextHourProvider.class})
public class NextHourProviderTest {
	
	private ZonedDateTime entryZonedDateTime = ZonedDateTime.parse("2017-01-01T12:39:50z[UTC]");
	private ZonedDateTime truncatedZonedDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
	
	@Produces @Mock
	private HourlyAverageNextHourRepository hourlyAverageNextHourRepository;
	
	@Produces @Mock
	private MagPlasmaCalculatedValuesNextHourRepository magPlasmaNextHourRepository;
	
	@Produces @Mock
	private NextHourValidator nextHourValidator;
	
	@Mock
	@Produces
	private DateTimeHelper dateTimeHelper;
	
	@Inject
	private NextHourProvider nextHourProvider; 
	
	@Test
	public void getNextHour_called_returnNextHourFromHourlyAverage() {
		when(hourlyAverageNextHourRepository.getNextHour()).thenReturn(entryZonedDateTime);
		when(nextHourValidator.validate(entryZonedDateTime)).thenReturn(true);
		when(dateTimeHelper.truncateToMinute(entryZonedDateTime)).thenReturn(truncatedZonedDateTime);
		
		ZonedDateTime nextHour = nextHourProvider.getNextHour();
		
		verify(hourlyAverageNextHourRepository, times(1)).getNextHour();
		assertEquals(truncatedZonedDateTime, nextHour);
		verify(dateTimeHelper).truncateToMinute(entryZonedDateTime); 
	}
	
	@Test
	public void getNextHour_called_returnNextHourFromMagPlasmaNextHour() {
		when(hourlyAverageNextHourRepository.getNextHour()).thenReturn(null);
		when(magPlasmaNextHourRepository.getNextHour()).thenReturn(entryZonedDateTime);
		when(nextHourValidator.validate(entryZonedDateTime)).thenReturn(true);
		when(dateTimeHelper.truncateToMinute(entryZonedDateTime)).thenReturn(truncatedZonedDateTime);
		
		ZonedDateTime nextHour = nextHourProvider.getNextHour();
		
		verify(hourlyAverageNextHourRepository, times(1)).getNextHour();
		verify(magPlasmaNextHourRepository, times(1)).getNextHour();
		assertEquals(truncatedZonedDateTime, nextHour);
		verify(dateTimeHelper).truncateToMinute(entryZonedDateTime); 
	}
	
	@Test
	public void getNextHour_called_returnNull() {
		when(hourlyAverageNextHourRepository.getNextHour()).thenReturn(null);
		when(magPlasmaNextHourRepository.getNextHour()).thenReturn(null);
		
		ZonedDateTime nextHour = nextHourProvider.getNextHour();
		
		verify(hourlyAverageNextHourRepository, times(1)).getNextHour();
		verify(magPlasmaNextHourRepository, times(1)).getNextHour();
		assertNull(nextHour);
	}
	 
	@Test
	public void getNextHour_called_validatedAndReturnsNull() {
		when(hourlyAverageNextHourRepository.getNextHour()).thenReturn(entryZonedDateTime);
		when(magPlasmaNextHourRepository.getNextHour()).thenReturn(null);
		when(nextHourValidator.validate(entryZonedDateTime)).thenReturn(false);
		
		ZonedDateTime nextHour = nextHourProvider.getNextHour();
		
		verify(hourlyAverageNextHourRepository, times(1)).getNextHour();
		verify(nextHourValidator, times(1)).validate(entryZonedDateTime);
		assertNull(nextHour); 
	}
	
	@Test
	public void getNextHour_called_validatedAndReturnsDate() {
		when(hourlyAverageNextHourRepository.getNextHour()).thenReturn(entryZonedDateTime);
		when(magPlasmaNextHourRepository.getNextHour()).thenReturn(null);
		when(nextHourValidator.validate(entryZonedDateTime)).thenReturn(true);
		when(dateTimeHelper.truncateToMinute(entryZonedDateTime)).thenReturn(truncatedZonedDateTime);
		
		ZonedDateTime nextHour = nextHourProvider.getNextHour();
		
		verify(hourlyAverageNextHourRepository, times(1)).getNextHour();
		verify(nextHourValidator, times(1)).validate(entryZonedDateTime);
		assertNotNull(nextHour); 
		assertEquals(truncatedZonedDateTime, nextHour);
		verify(dateTimeHelper).truncateToMinute(entryZonedDateTime);
	}
	
}
