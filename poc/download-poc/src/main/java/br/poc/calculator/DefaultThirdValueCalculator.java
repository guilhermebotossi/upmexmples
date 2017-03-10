package br.poc.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.poc.calculator.dto.MagPlasmaPair;

public class DefaultThirdValueCalculator implements ThirdValueCalculator {
	/**
	 * RMP= (10.22 + 1.29 * TANH (0.184 * (IMF_BBZ + 8.14)))) * SWD_DPR ^(-1.0/6.6) 
	 */

	@Override
	public double calculate(MagPlasmaPair magPlasmaPair) {
		if(magPlasmaPair == null) 
			throw new RuntimeException("Parâmetro \"magPlasmaPair\" null/empty.");
		
	
		double rmp = (10.22  + 1.29 * (Math.tanh(0.184 * (magPlasmaPair.getBzGsm() + 8.14)))) * Math.pow(magPlasmaPair.getDpr(), (-1.0/6.6));
		
		rmp = new BigDecimal(rmp).setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		return rmp;
	}

}
