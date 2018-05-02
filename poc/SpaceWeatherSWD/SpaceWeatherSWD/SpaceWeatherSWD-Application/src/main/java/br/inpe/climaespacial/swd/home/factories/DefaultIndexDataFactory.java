
package br.inpe.climaespacial.swd.home.factories;

import br.inpe.climaespacial.swd.commons.factories.Factory;
import br.inpe.climaespacial.swd.home.dtos.IndexData;
import br.inpe.climaespacial.swd.home.dtos.IndexEntry;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultIndexDataFactory implements IndexDataFactory {
    
    @Inject
    private Factory<IndexData> indexDataFactory;
        
    @Override
    public IndexData create(List<IndexEntry> bIndexEntryList, List<IndexEntry> cIndexEntryList, List<IndexEntry> vIndexEntryList, List<IndexEntry> zIndexEntryList) {
        IndexData id = indexDataFactory.create();
        id.setbEntries(bIndexEntryList);
        id.setcEntries(cIndexEntryList);
        id.setvEntries(vIndexEntryList);
        id.setzEntries(zIndexEntryList);
        return id;        
    }
    
}
