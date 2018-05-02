
package br.inpe.climaespacial.swd.indexes.v.mappers;

import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import java.util.List;

public interface VIndexMapper {
    
    List<VIndex> map(List<VIndexEntity> vIndexEntityList);
    
}
