package br.poc.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.poc.calculator.dto.MagPlasmaPair;

/**
SWD_DPR = 1.672E-6 * SWD_DEN * SWD_VEL * SWD_VEL  
*/
public class DefaultSecondValueCalculator implements SecondValueCalculator {

	@Override
	public double calculate(MagPlasmaPair mpp) {
		if(mpp == null)
			throw new RuntimeException("Parâmetro \"magPlasmaPair\" null/empty.");
		
		double dpr = 1.672E-6 * mpp.getDensity() * mpp.getSpeed() * mpp.getSpeed();
		dpr = new BigDecimal(dpr).setScale(2, RoundingMode.HALF_UP).doubleValue();
		
		return dpr;
	}

}
