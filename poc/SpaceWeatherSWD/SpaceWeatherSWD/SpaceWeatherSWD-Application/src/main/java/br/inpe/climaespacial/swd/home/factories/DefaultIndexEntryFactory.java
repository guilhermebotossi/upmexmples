package br.inpe.climaespacial.swd.home.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.home.dtos.IndexEntry;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultIndexEntryFactory implements IndexEntryFactory {

    @Inject
    private HelperFactory<IndexEntry> indexEntryHelperFactory;

    @Override
    public IndexEntry create() {
        return indexEntryHelperFactory.create();
    }

}
