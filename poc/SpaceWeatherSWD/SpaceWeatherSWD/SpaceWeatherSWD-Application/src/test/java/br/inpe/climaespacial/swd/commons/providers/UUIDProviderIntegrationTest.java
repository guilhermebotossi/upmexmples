package br.inpe.climaespacial.swd.commons.providers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.jcabi.matchers.RegexMatchers;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultUUIDProvider.class)
public class UUIDProviderIntegrationTest {

	@Inject
	private UUIDProvider uuidProvider;

	@Test
	public void getUUID_called_returnsUUID() {
		UUID uuid = uuidProvider.getUUID();
		assertNotNull(uuid);
		String u = uuid.toString();
		assertEquals(36, u.length());
		assertThat(u, RegexMatchers.matchesPattern(
				"^([a-z]|[\\d]){8}\\-([a-z]|[\\d]){4}\\-([a-z]|[\\d]){4}\\-([a-z]|[\\d]){4}\\-([a-z]|[\\d]){12}$"));
	}
}
