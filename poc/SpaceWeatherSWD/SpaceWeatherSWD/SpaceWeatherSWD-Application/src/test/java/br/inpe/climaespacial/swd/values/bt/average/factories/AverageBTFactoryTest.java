package br.inpe.climaespacial.swd.values.bt.average.factories;


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
import br.inpe.climaespacial.swd.values.bt.average.dtos.AverageBT;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultAverageBTFactory.class})
public class AverageBTFactoryTest {
	
	@Inject
	private AverageBTFactory averageBTFactory;
	
	@Test
	public void create_called_succeeds() {

        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;
		AverageBT abt = averageBTFactory.create(zdt, value); 
		assertNotNull(abt);
        assertSame(zdt, abt.getTimeTag());
        assertSame(value, abt.getValue());
		assertEquals(AverageBT.class, abt.getClass());
		
		
	}

}
