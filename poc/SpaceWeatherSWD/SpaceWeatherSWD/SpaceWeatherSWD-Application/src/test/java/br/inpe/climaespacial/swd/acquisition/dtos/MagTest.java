package br.inpe.climaespacial.swd.acquisition.dtos;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(Mag.class)
public class MagTest {

	private static final double DELTA = 0.001;

	private static final double VALUE = 1.0;

	@Inject
	private Mag mag;

	@Test
	public void timeTagTest() {
		ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

		mag.setTimeTag(timeTag);

		assertEquals(timeTag, mag.getTimeTag());
	}

	@Test
	public void bxGsmTest() {
		mag.setBxGsm(VALUE);

		assertEquals(VALUE, mag.getBxGsm(), DELTA);
	}

	@Test
	public void byGsmTest() {
		mag.setByGsm(VALUE);

		assertEquals(VALUE, mag.getByGsm(), DELTA);
	}

	@Test
	public void bzGsmTest() {
		mag.setBzGsm(VALUE);

		assertEquals(VALUE, mag.getBzGsm(), DELTA);
	}

	@Test
	public void latGsmTest() {
		mag.setLatGsm(VALUE);

		assertEquals(VALUE, mag.getLatGsm(), DELTA);
	}

	@Test
	public void lonGsmTest() {
		mag.setLonGsm(VALUE);

		assertEquals(VALUE, mag.getLonGsm(), DELTA);
	}

	@Test
	public void btTest() {
		mag.setBt(VALUE);

		assertEquals(VALUE, mag.getBt(), DELTA);
	}

	@Test
	public void toString_called_returnsStringRepresentation() {
		mag.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
		mag.setBxGsm(VALUE);
		mag.setByGsm(VALUE + 1);
		mag.setBzGsm(VALUE + 2);
		mag.setLatGsm(VALUE + 3);
		mag.setLonGsm(VALUE + 4);
		mag.setBt(VALUE + 5);

		String r = mag.toString();

		assertEquals("Mag [timeTag=2017-01-01T12:00Z[UTC], bxGsm=1.0, byGsm=2.0, "
				+ "bzGsm=3.0, latGsm=4.0, lonGsm=5.0, bt=6.0]", r);
	}
}
