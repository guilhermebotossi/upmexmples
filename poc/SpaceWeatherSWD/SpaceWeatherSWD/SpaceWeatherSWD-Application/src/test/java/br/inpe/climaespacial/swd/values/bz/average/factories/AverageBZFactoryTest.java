package br.inpe.climaespacial.swd.values.bz.average.factories;


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
import br.inpe.climaespacial.swd.values.bz.average.dtos.AverageBZ;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultAverageBZFactory.class})
public class AverageBZFactoryTest {
	
	@Inject
	private AverageBZFactory averageBZFactory;
	
	@Test
	public void create_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;
        
		AverageBZ abz = averageBZFactory.create(zdt, value);
		
		assertNotNull(abz);
		assertSame(zdt, abz.getTimeTag());
		assertSame(value, abz.getValue());
		assertEquals(AverageBZ.class, abz.getClass());
		
		
	}

}
