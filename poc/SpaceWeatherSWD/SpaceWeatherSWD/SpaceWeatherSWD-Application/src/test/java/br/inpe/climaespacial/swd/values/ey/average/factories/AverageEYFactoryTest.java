package br.inpe.climaespacial.swd.values.ey.average.factories;


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
import br.inpe.climaespacial.swd.values.ey.average.dtos.AverageEY;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultAverageEYFactory.class})
public class AverageEYFactoryTest {
	
	@Inject
	private AverageEYFactory averageEYFactory;
	
	@Test
	public void create_called_succeeds() {
	    ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
	    Double value = 2.0;

	    AverageEY aey = averageEYFactory.create(zdt, value); 

	    assertNotNull(aey);
	    assertSame(zdt, aey.getTimeTag());
	    assertSame(value, aey.getValue());
	    assertEquals(AverageEY.class, aey.getClass());
	}

}
