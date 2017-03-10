package br.poc.test;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.poc.calculator.DefaultHourlyAverageCalculator;
import br.poc.calculator.HourlyAverageCalculator;
import br.poc.calculator.dto.HourlyAverage;
import br.poc.calculator.dto.MagPlasmaPair;

public class HourlyAverageCalculatorTest {

	@Test
	public void calculate_calledWithNullArgument_throws() {
		HourlyAverageCalculator hac = new DefaultHourlyAverageCalculator();
		RuntimeException re = null;
		
		try {
			hac.calculate(null);
		} catch(RuntimeException e) {
			re = e;
		}
		
		assertNotNull(re);
		assertEquals("Parametro \"listmagPlasmaPair\" null/empty", re.getMessage());
		
	}
	
	@Test
	public void calculate_calledWithLessThan31Elements_succeed() {
		HourlyAverageCalculator hac = new DefaultHourlyAverageCalculator();
		List<MagPlasmaPair> pairs = new ArrayList<>();
		
		HourlyAverage ha = hac.calculate(pairs);
		
		assertNotNull(ha);
		assertNull(ha.getBt());
		assertNull(ha.getBxGsm());
		assertNull(ha.getByGsm());
		assertNull(ha.getBzGsm());
		assertNull(ha.getDensity());
		assertNull(ha.getDpr());
		assertNull(ha.getEy());
		assertNull(ha.getSpeed());
		assertNull(ha.getTemperature());
		assertNull(ha.getRmp());
	}
	
	@Test
	public void calculate_calledWith31ElementsOrMore_succeed() {
		HourlyAverageCalculator hac = new DefaultHourlyAverageCalculator();
		List<MagPlasmaPair> pairs = TestHelper.getMagPlasmaPair();
		
		HourlyAverage ha = hac.calculate(pairs);
		
		assertNotNull(ha);
		assertEquals(4.565524476, ha.getBt(), 0.001);
		assertEquals(2.70193007, ha.getBxGsm(), 0.001);
		assertEquals(-1.441384615, ha.getByGsm(), 0.001);
		assertEquals(-0.3478881119, ha.getBzGsm(), 0.001);
		assertEquals(6.35820979, ha.getDensity(), 0.001);
		assertEquals(472655.0601, ha.getTemperature(), 0.001);
		assertEquals(688.0033566, ha.getSpeed(), 0.001);
		assertEquals(0.2615664336, ha.getEy(), 0.001);
		assertEquals(4.941846154, ha.getDpr(), 0.001);
		assertEquals(8.915832168, ha.getRmp(), 0.001);
	}
	
	

}
