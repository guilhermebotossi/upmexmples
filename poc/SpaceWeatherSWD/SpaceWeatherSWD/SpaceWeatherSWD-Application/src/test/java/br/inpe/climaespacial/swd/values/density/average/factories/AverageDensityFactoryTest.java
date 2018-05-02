package br.inpe.climaespacial.swd.values.density.average.factories;


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
import br.inpe.climaespacial.swd.values.density.average.dtos.AverageDensity;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultAverageDensityFactory.class})
public class AverageDensityFactoryTest {
	
	@Inject
	private AverageDensityFactory averageDensityFactory;
	
	@Test
	public void create_called_succeeds() {
	    ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
	    Double value = 2.0;

	    AverageDensity ad = averageDensityFactory.create(zdt, value); 

	    assertNotNull(ad);
	    assertSame(zdt, ad.getTimeTag());
	    assertSame(value, ad.getValue());
	    assertEquals(AverageDensity.class, ad.getClass());
		
	}

}
