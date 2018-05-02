package br.inpe.climaespacial.swd.values.factories;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.values.dtos.ValueEntry;

@Dependent
public class DefaultValueEntryFactory implements ValueEntryFactory {

    @Inject
    private HelperFactory<ValueEntry> valueEntryfactory;
    
    @Override
    public ValueEntry create() {
        return valueEntryfactory.create();
    }

}
