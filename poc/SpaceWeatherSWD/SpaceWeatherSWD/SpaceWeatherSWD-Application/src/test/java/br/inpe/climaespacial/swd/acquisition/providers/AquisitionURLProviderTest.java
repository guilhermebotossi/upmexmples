package br.inpe.climaespacial.swd.acquisition.providers;

import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultAquisitionURLProvider.class)
public class AquisitionURLProviderTest {

	@Inject
	private AquisitionURLProvider aquisitionURLProvider;

	@Test
	public void getURL_called_succeeds() {

		assertEquals("http://services.swpc.noaa.gov/products/solar-wind/", aquisitionURLProvider.getURL());

	}

}