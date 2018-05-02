
package br.inpe.climaespacial.swd.indexes.c.mappers;

import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import java.util.List;

public interface CIndexMapper {
    
    List<CIndex> map(List<CIndexEntity> cIndexEntityList);
    
}
