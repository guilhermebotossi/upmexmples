package br.inpe.climaespacial.swd.average.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.average.providers.HourlyAverageNextHourRepository;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultHourlyAverageNextHourRepository.class,
		EntityManagerFactoryProducer.class,
	    EntityManagerProducer.class})
public class HourlyAverageNextHourRepositoryIntegrationTest extends BaseIntegrationTest {
	
	@Inject
	private EntityManager entityManager;
	
	@Inject
	private HourlyAverageNextHourRepository hourlyAverageNextHourRepository;
	
	@Test
	@InRequestScope
	public void getNextHour_called_returnedNull() {
		ZonedDateTime nextHour = hourlyAverageNextHourRepository.getNextHour(); 
		
		assertNull(nextHour);
	}
	
	
	@Test
	@InRequestScope
	public void getNextHour_called_returnedValidDateTime() {
		HourlyAverageEntity hae = createHourlyAverageEntity();
		ZonedDateTime zdt = hae.getTimeTag();
		ZonedDateTime expected = zdt.plusHours(1);
		entityManager.persist(hae);
		
		ZonedDateTime nextHour = hourlyAverageNextHourRepository.getNextHour();
		
		assertNotNull(nextHour);
		assertEquals(expected, nextHour);
	}


	private HourlyAverageEntity createHourlyAverageEntity() {
		HourlyAverageEntity hae = new HourlyAverageEntity();
		hae.setId(UUID.randomUUID());
		hae.setCreationDate(ZonedDateTime.now());
		hae.setModificationDate(ZonedDateTime.now());
		hae.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
		return hae;
	}
	
}
