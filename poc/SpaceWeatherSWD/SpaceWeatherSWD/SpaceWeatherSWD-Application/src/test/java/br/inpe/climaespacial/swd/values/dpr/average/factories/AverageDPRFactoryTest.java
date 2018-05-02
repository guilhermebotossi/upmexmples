package br.inpe.climaespacial.swd.values.dpr.average.factories;


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
import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultAverageDPRFactory.class})
public class AverageDPRFactoryTest {
	
	@Inject
	private AverageDPRFactory averageDPRFactory;
	
	@Test
	public void create_called_succeeds() {
	    ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
	    Double value = 2.0;

	    AverageDPR adpr = averageDPRFactory.create(zdt, value); 

	    assertNotNull(adpr);
	    assertSame(zdt, adpr.getTimeTag());
	    assertSame(value, adpr.getValue());
	    assertEquals(AverageDPR.class, adpr.getClass());
	}

}
