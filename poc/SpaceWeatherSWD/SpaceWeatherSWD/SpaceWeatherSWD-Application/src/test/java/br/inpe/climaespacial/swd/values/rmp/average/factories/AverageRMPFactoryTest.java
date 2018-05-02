package br.inpe.climaespacial.swd.values.rmp.average.factories;


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
import br.inpe.climaespacial.swd.values.rmp.average.dtos.AverageRMP;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultAverageRMPFactory.class})
public class AverageRMPFactoryTest {
	
	@Inject
	private AverageRMPFactory averageRMPFactory;
	
	@Test
	public void create_called_succeeds() {
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
		Double value = 2.0;

		AverageRMP armp = averageRMPFactory.create(zdt, value); 

		assertNotNull(armp);
		assertSame(zdt, armp.getTimeTag());
		assertSame(value, armp.getValue());
		assertEquals(AverageRMP.class, armp.getClass());
		
		
	}

}
