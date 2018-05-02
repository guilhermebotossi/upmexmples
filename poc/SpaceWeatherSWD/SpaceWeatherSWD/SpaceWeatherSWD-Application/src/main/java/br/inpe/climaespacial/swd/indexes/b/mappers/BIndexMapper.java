
package br.inpe.climaespacial.swd.indexes.b.mappers;

import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import java.util.List;

public interface BIndexMapper {
    
    List<BIndex> map(List<BIndexEntity> bIndexEntityList);
    
}
