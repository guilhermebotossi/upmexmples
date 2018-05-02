
package br.inpe.climaespacial.swd.calculation.mappers;

import br.inpe.climaespacial.swd.calculation.entities.MagPlasmaEntity;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;
import java.util.List;

public interface MagPlasmaMapper {
    
    List<MagPlasma> map(List<MagPlasmaEntity> mpel);
    
}
