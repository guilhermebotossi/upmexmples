package br.inpe.climaespacial.swd.values.temperature.value.factories;


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
import br.inpe.climaespacial.swd.values.temperature.value.dtos.Temperature;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultTemperatureFactory.class})
public class TemperatureFactoryTest {
	
	@Inject
	private TemperatureFactory temperatureFactory;
	
	@Test
	public void create_called_succeeds() {
		
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
		Double value = 2.0;

		Temperature temperature = temperatureFactory.create(zdt, value); 

		assertNotNull(temperature);
		assertSame(zdt, temperature.getTimeTag());
		assertSame(value, temperature.getValue());
		assertEquals(Temperature.class, temperature.getClass());
	}

}
