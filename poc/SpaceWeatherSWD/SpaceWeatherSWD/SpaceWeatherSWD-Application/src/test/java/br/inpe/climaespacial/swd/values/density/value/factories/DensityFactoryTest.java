package br.inpe.climaespacial.swd.values.density.value.factories;


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
import br.inpe.climaespacial.swd.values.density.value.dtos.Density;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class,
					DefaultDensityFactory.class})
public class DensityFactoryTest {
	
	@Inject
	private DensityFactory densityFactory;
	
	@Test
	public void create_called_succeeds() {
	    ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
	    Double value = 2.0;

	    Density bz = densityFactory.create(zdt, value); 

	    assertNotNull(bz);
	    assertSame(zdt, bz.getTimeTag());
	    assertSame(value, bz.getValue());
	    assertEquals(Density.class, bz.getClass());
	}

}
