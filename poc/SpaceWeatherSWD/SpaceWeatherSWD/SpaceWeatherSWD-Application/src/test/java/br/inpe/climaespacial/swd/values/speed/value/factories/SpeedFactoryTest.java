package br.inpe.climaespacial.swd.values.speed.value.factories;


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
import br.inpe.climaespacial.swd.values.speed.value.dtos.Speed;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultSpeedFactory.class})
public class SpeedFactoryTest {
	
	@Inject
	private SpeedFactory speedFactory;
	
	@Test
	public void create_called_succeeds() {
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
		Double value = 2.0;

		Speed speed = speedFactory.create(zdt, value); 

		assertNotNull(speed);
		assertSame(zdt, speed.getTimeTag());
		assertSame(value, speed.getValue());
		assertEquals(Speed.class, speed.getClass());
	}

}
