package br.inpe.climaespacial.swd.values.bx.average.factories;


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
import br.inpe.climaespacial.swd.values.bx.average.dtos.AverageBX;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultAverageBXFactory.class})
public class AverageBXFactoryTest {
	
	@Inject
	private AverageBXFactory averageBXFactory;
	
	@Test
	public void create_called_succeeds() {

        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;
		AverageBX abx = averageBXFactory.create(zdt, value); 
		assertNotNull(abx);
        assertSame(zdt, abx.getTimeTag());
        assertSame(value, abx.getValue());
		assertEquals(AverageBX.class, abx.getClass());
		
		
	}

}
