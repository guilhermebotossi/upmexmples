package br.inpe.climaespacial.swd.calculation.services;

import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;

public interface RmpValueCalculator {

	Double calculate(MagPlasma magPlasma, Double dpr);

}
