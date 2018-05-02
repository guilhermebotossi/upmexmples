
package br.inpe.climaespacial.swd.home.mappers;

import br.inpe.climaespacial.swd.home.dtos.IndexEntry;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import java.util.List;

public interface IndexEntryMapper {
    
    List<IndexEntry> mapB(List<BIndex> bIndexList);
    
    List<IndexEntry> mapC(List<CIndex> cIndexList);
    
    List<IndexEntry> mapV(List<VIndex> vIndexList);
    
    List<IndexEntry> mapZ(List<ZIndex> zIndexList);
    
}
