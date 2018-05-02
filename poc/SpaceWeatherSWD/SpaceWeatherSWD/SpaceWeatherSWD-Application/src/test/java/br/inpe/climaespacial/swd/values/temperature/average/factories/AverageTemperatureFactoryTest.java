package br.inpe.climaespacial.swd.values.temperature.average.factories;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.values.temperature.average.dtos.AverageTemperature;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultAverageTemperatureFactory.class})
public class AverageTemperatureFactoryTest {
	
	@Inject
	private AverageTemperatureFactory averageTemperatureFactory;
	
	@Test
	public void create_called_succeeds() {
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
		Double value = 2.0;

		AverageTemperature at = averageTemperatureFactory.create(zdt, value); 

		assertNotNull(at);
		assertSame(zdt, at.getTimeTag());
		assertSame(value, at.getValue());
		assertEquals(AverageTemperature.class, at.getClass());
	}

}
