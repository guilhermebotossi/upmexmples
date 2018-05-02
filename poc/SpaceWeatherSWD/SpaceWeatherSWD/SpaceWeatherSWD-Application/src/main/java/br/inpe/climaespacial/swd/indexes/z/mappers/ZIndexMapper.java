
package br.inpe.climaespacial.swd.indexes.z.mappers;

import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import java.util.List;

public interface ZIndexMapper {
    
    List<ZIndex> map(List<ZIndexEntity> zIndexEntityList);

    ZIndex map(ZIndexEntity zie);
    
}
