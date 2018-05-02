package br.inpe.climaespacial.swd.values.bx.value.factories;


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
import br.inpe.climaespacial.swd.values.bx.value.dtos.BX;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultBXFactory.class})
public class BXFactoryTest {
	
	@Inject
	private BXFactory bxFactory;
	
	@Test
	public void create_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;
		BX bx = bxFactory.create(zdt, value);
		
        assertSame(zdt, bx.getTimeTag());
        assertSame(value, bx.getValue());
		assertNotNull(bx);
		assertEquals(BX.class, bx.getClass());
		
		
	}

}
