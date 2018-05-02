package br.inpe.climaespacial.swd.values.by.average.factories;


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
import br.inpe.climaespacial.swd.values.by.average.dtos.AverageBY;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultAverageBYFactory.class})
public class AverageBYFactoryTest {
	
	@Inject
	private AverageBYFactory averageBYFactory;
	
	@Test
	public void create_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;
        
		AverageBY aby = averageBYFactory.create(zdt, value); 
		
		assertNotNull(aby);
        assertSame(zdt, aby.getTimeTag());
        assertSame(value, aby.getValue());
		assertEquals(AverageBY.class, aby.getClass());
		
		
	}

}
