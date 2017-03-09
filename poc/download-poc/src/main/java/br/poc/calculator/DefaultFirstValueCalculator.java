package br.poc.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.poc.calculator.dto.MagPlasmaPair;

public class DefaultFirstValueCalculator implements FirstValueCalculator {

	/**
	 * EY=-0.9965 * SWD_VEL * IMF_BBZ/1000. 
	 */
	@Override
	public double calculate(MagPlasmaPair magPlasmaPair) {
		if(magPlasmaPair == null)
			throw new RuntimeException("Parâmetro \"magPlasmaPair\" null/empty.");
		
		double ey = -0.9965 * magPlasmaPair.getSpeed() * magPlasmaPair.getBzGsm()/1000.0;
		
		ey = new BigDecimal(ey).setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		return ey; 
		
	}

}
