package br.poc.calculator;

import java.util.List;

import br.poc.calculator.dto.HourlyAverage;
import br.poc.calculator.dto.MagPlasmaPair;

public interface HourlyAverageCalculator {

	HourlyAverage calculate(List<MagPlasmaPair> magPlasmaPair);

}
