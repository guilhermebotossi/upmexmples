package br.inpe.climaespacial.swd.values.by.value.factories;


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
import br.inpe.climaespacial.swd.values.by.value.dtos.BY;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultBYFactory.class})
public class BYFactoryTest {
	
	@Inject
	private BYFactory byFactory;
	
	@Test
	public void create_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

		BY by = byFactory.create(zdt, value); 
		
		assertNotNull(by);
        assertSame(zdt, by.getTimeTag());
        assertSame(value, by.getValue());
		assertEquals(BY.class, by.getClass());
	}

}
