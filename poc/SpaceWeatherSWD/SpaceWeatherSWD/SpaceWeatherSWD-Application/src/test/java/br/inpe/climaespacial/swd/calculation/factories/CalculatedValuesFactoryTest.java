package br.inpe.climaespacial.swd.calculation.factories;

import br.inpe.climaespacial.swd.calculation.dtos.CalculatedValues;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.ZonedDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultCalculatedValuesFactory.class)
public class CalculatedValuesFactoryTest {

	@Inject
	private CalculatedValuesFactory calculatedValuesFactory;

	@Test
	public void create_called_returnsCalculatedValues() {

		double delta = 0.001;
		ZonedDateTime expectedZdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

		CalculatedValues cv = calculatedValuesFactory.create(1.0, 2.0, 3.0, expectedZdt);

		assertNotNull(cv);
		assertEquals(1.0, cv.getEy(), delta);
		assertEquals(2.0, cv.getDpr(), delta);
		assertEquals(3.0, cv.getRmp(), delta);
		assertEquals(expectedZdt, cv.getTimeTag());

	}

}
