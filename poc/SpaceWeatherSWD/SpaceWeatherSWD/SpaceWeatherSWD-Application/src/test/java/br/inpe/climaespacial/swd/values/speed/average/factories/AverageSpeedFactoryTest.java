package br.inpe.climaespacial.swd.values.speed.average.factories;


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
import br.inpe.climaespacial.swd.values.speed.average.dtos.AverageSpeed;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultAverageSpeedFactory.class})
public class AverageSpeedFactoryTest {
	
	@Inject
	private AverageSpeedFactory AverageSpeedFactory;
	
	@Test
	public void create_called_succeeds() {
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
		Double value = 2.0;

		AverageSpeed as = AverageSpeedFactory.create(zdt, value); 

		assertNotNull(as);
		assertSame(zdt, as.getTimeTag());
		assertSame(value, as.getValue());
		assertEquals(AverageSpeed.class, as.getClass());	
	}

}
