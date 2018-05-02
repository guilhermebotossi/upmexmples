package br.inpe.climaespacial.swd.commons.factories;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.ZonedDateTime;
import java.util.AbstractMap.SimpleEntry;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultSimpleEntryFactory.class})
public class SimpleEntryFactoryTest {
	
	@Inject
	private SimpleEntryFactory<ZonedDateTime, String> simpleEntryFactory;

	@Test
	public void create_called_returnsInstanceOfList() {
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

		SimpleEntry<ZonedDateTime, String> se = simpleEntryFactory.create(zdt, "Teste");

		assertNotNull(se);
		assertThat(se, instanceOf(SimpleEntry.class));
	}

}
