package br.poc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jglue.cdiunit.AdditionalClasses;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import br.poc.calculator.DefaultFirstValueCalculator;
import br.poc.calculator.FirstValueCalculator;
import br.poc.calculator.dto.MagPlasmaPair;

/**
 * EY=-0.9965 * SWD_VEL * IMF_BBZ/1000. 
 */

@RunWith(Theories.class)
@AdditionalClasses(DefaultFirstValueCalculator.class)
public class FirstValueCalculatorTest {
	
	private FirstValueCalculator fvc;
	
	@DataPoints
	public static String[][] pairs = TestHelper.getPairs();
	
	@Before
	public void beforeTest() {
		fvc = new DefaultFirstValueCalculator();
	}
	
	@Test
	public void calculate_calledWithNullArgument_throws() {
		RuntimeException re = null;
		
		try {
			fvc.calculate(null);
		} catch(RuntimeException e) {
			re = e;
		}
			
		assertNotNull(re);
		assertEquals("Parâmetro \"magPlasmaPair\" null/empty.", re.getMessage());
	}
	
	@Theory
	public void calculate_calledWithValidArgument_succeeds(String[] data) {
		MagPlasmaPair mpp = new MagPlasmaPair();
		mpp.setSpeed(Double.valueOf(data[4]).doubleValue());
		mpp.setBzGsm(Double.valueOf(data[7]).doubleValue());
		
		double ey = fvc.calculate(mpp);
		
		assertEquals(Double.valueOf(data[8]).doubleValue(), ey, 0.00);
		
	}

}
