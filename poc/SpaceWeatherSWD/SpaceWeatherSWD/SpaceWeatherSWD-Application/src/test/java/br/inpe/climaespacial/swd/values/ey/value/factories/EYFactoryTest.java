package br.inpe.climaespacial.swd.values.ey.value.factories;


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
import br.inpe.climaespacial.swd.values.ey.value.dtos.EY;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultEYFactory.class})
public class EYFactoryTest {
	
	@Inject
	private EYFactory eyFactory;
	
	@Test
	public void create_called_succeeds() {
	    ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
	    Double value = 2.0;

	    EY ey = eyFactory.create(zdt, value); 

	    assertNotNull(ey);
	    assertSame(zdt, ey.getTimeTag());
	    assertSame(value, ey.getValue());
	    assertEquals(EY.class, ey.getClass());
	}

}
