package br.inpe.climaespacial.swd.values.dpr.value.factories;


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
import br.inpe.climaespacial.swd.values.dpr.value.dtos.DPR;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultDPRFactory.class})
public class DPRFactoryTest {
	
	@Inject
	private DPRFactory dprFactory;
	
	@Test
	public void create_called_succeeds() {
	    ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
	    Double value = 2.0;

	    DPR dpr = dprFactory.create(zdt, value); 

	    assertNotNull(dpr);
	    assertSame(zdt, dpr.getTimeTag());
	    assertSame(value, dpr.getValue());
	    assertEquals(DPR.class, dpr.getClass());
	}

}
