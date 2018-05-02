package br.inpe.climaespacial.swd.values.rmp.value.factories;


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
import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultRMPFactory.class})
public class RMPFactoryTest {
	
	@Inject
	private RMPFactory rmpFactory;
	
	@Test
	public void create_called_succeeds() {
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
		Double value = 2.0;

		RMP rmp = rmpFactory.create(zdt, value); 

		assertNotNull(rmp);
		assertSame(zdt, rmp.getTimeTag());
		assertSame(value, rmp.getValue());
		assertEquals(RMP.class, rmp.getClass());
	}
}
