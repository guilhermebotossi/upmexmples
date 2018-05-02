package br.inpe.climaespacial.swd.calculation.repositories;

import java.util.List;

import br.inpe.climaespacial.swd.calculation.dtos.CalculatedValues;

public interface CalculatedValuesWriterRepository {

	void save(List<CalculatedValues> calculatedValuesList);

}
