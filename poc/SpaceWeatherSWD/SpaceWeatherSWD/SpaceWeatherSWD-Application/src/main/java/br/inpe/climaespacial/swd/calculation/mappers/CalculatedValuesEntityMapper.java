package br.inpe.climaespacial.swd.calculation.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.calculation.dtos.CalculatedValues;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;

public interface CalculatedValuesEntityMapper {

	List<CalculatedValuesEntity> map(List<CalculatedValues> calculatedValuesList);

}
