package br.inpe.climaespacial.swd.acquisition.providers;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultDateTimeProvider.class})
public class DateTimeProviderTest {

	@Inject
	private DateTimeProvider dateTimeProvider;
	
	@Test
	public void getNow_called_succeeds() {
		ZonedDateTime zdt = ZonedDateTime.now();
		assertTrue(dateTimeProvider.getNow().compareTo(zdt) >= 0);
	}
}
