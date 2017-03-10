package br.poc.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import br.poc.calculator.DefaultSecondValueCalculator;
import br.poc.calculator.SecondValueCalculator;
import br.poc.calculator.dto.MagPlasmaPair;


/**
	SWD_DPR = 1.672E-6 * SWD_DEN * SWD_VEL * SWD_VEL  
 */
@RunWith(Theories.class)
public class SecondValueCalculatorTest {
	
	private SecondValueCalculator svc;
	
	@Before
	public void beforeTest() {
		svc = new DefaultSecondValueCalculator();
	}
	
	@DataPoints
	public static String[][] data = TestHelper.getPairs();
	
	@Test
	public void calculate_calledWithNullArgument_throws() {
		RuntimeException re = null;
		
		try {
			svc.calculate(null);
		} catch(RuntimeException e) {
			re = e;
		}	
		
		assertNotNull(re);
		assertEquals("Parâmetro \"magPlasmaPair\" null/empty.", re.getMessage());
	}

	@Theory
	public void calculate_calledWithValidArgument_succeed(String[] data) {
		MagPlasmaPair mpp = new MagPlasmaPair();
		mpp.setSpeed(Double.valueOf(data[7]).doubleValue());
		mpp.setDensity(Double.valueOf(data[5]).doubleValue());
		
		double dpr = svc.calculate(mpp);
		
		assertEquals(Double.valueOf(data[9]).doubleValue(), dpr, 0.00);
	}
}
