package br.poc.test;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import br.poc.calculator.DefaultThirdValueCalculator;
import br.poc.calculator.ThirdValueCalculator;
import br.poc.calculator.dto.MagPlasmaPair;

/**
 * RMP= 10.22 + 1.29 * TANH (0.184 * IMF_BBZ + 8.14)))) * SWD_DPR ^(-1.0/6.6) 
 */
@RunWith(Theories.class)
public class ThirdValueCalculatorTest {
	
	@DataPoints
	public static String[][] pairs = TestHelper.getPairs();
	
	@Test
	public void calculate_calledWithInvalidArgument_throws() {
		ThirdValueCalculator tvc = new  DefaultThirdValueCalculator();
		RuntimeException re = null;
		
		try {
			tvc.calculate(null);
		} catch(RuntimeException e) {
			re = e;
		}
		
		assertNotNull(re);
		assertEquals("Parâmetro \"magPlasmaPair\" null/empty.", re.getMessage());
	}
	
	@Theory
	@Ignore
	public void calculate_calledWithValidArgument_succeeds(String[] data) {
		MagPlasmaPair mpp = new MagPlasmaPair();
		mpp.setDpr(Double.valueOf(data[9]).doubleValue());
		mpp.setBzGsm(Double.valueOf(data[4]).doubleValue());
		
		ThirdValueCalculator tvc = new  DefaultThirdValueCalculator();
		
		double rmp = tvc.calculate(mpp);
		
		assertEquals(Double.valueOf(data[10]).doubleValue(), rmp, 0.00);
		
	}
	

}
