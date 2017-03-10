package br.poc.calculator;

import java.util.List;

import br.poc.calculator.dto.HourlyAverage;
import br.poc.calculator.dto.MagPlasmaPair;

public class DefaultHourlyAverageCalculator implements HourlyAverageCalculator {

	@Override
	public HourlyAverage calculate(List<MagPlasmaPair> listMagPlasmaPair) {
		if(listMagPlasmaPair == null)
			throw new RuntimeException("Parametro \"listmagPlasmaPair\" null/empty");
		
		HourlyAverage ha = new HourlyAverage();
		int size = listMagPlasmaPair.size();
		
		if(size > 31) {
			
			ha.setBt(listMagPlasmaPair.stream().mapToDouble(pair -> pair.getBt()).sum()/size);
			ha.setBxGsm(listMagPlasmaPair.stream().mapToDouble(pair -> pair.getBxGsm()).sum()/size);
			ha.setByGsm(listMagPlasmaPair.stream().mapToDouble(pair -> pair.getByGsm()).sum()/size);
			ha.setBzGsm(listMagPlasmaPair.stream().mapToDouble(pair -> pair.getBzGsm()).sum()/size);
			ha.setDensity(listMagPlasmaPair.stream().mapToDouble(pair -> pair.getDensity()).sum()/size);
			ha.setDpr(listMagPlasmaPair.stream().mapToDouble(pair -> pair.getDpr()).sum()/size);
			ha.setEy(listMagPlasmaPair.stream().mapToDouble(pair -> pair.getEy()).sum()/size);
			ha.setRmp(listMagPlasmaPair.stream().mapToDouble(pair -> pair.getRmp()).sum()/size);
			ha.setSpeed(listMagPlasmaPair.stream().mapToDouble(pair -> pair.getSpeed()).sum()/size);
			ha.setTemperature(listMagPlasmaPair.stream().mapToDouble(pair -> pair.getTemperature()).sum()/size);
		}
		return ha;
	}

}
