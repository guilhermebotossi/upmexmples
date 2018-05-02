
package br.inpe.climaespacial.swd.home.factories;

import br.inpe.climaespacial.swd.home.dtos.IndexData;
import br.inpe.climaespacial.swd.home.dtos.IndexEntry;
import java.util.List;

public interface IndexDataFactory {
    
    IndexData create(List<IndexEntry> bIndexEntryList, List<IndexEntry> cIndexEntryList, List<IndexEntry> vIndexEntryList, List<IndexEntry> zIndexEntryList);
    
}
