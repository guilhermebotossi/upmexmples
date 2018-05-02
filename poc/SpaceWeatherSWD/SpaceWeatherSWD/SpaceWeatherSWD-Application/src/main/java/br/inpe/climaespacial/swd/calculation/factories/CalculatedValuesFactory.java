package br.inpe.climaespacial.swd.calculation.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.calculation.dtos.CalculatedValues;

public interface CalculatedValuesFactory {

    CalculatedValues create(Double ey, Double dpr, Double rmp, ZonedDateTime expectedZdt);

}
