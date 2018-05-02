package br.inpe.climaespacial.swd.average.mappers;

import br.inpe.climaespacial.swd.average.entities.MagPlasmaCalculatedEntity;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;
import java.util.List;

public interface MagPlasmaCalculatedMapper {
    
    List<MagPlasmaCalculated> map(List<MagPlasmaCalculatedEntity> magPlasmaCalculatedEntityList);
    
}
