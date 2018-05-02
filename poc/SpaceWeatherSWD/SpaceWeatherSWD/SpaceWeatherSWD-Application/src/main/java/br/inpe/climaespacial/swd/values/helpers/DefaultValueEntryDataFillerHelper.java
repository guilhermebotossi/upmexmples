package br.inpe.climaespacial.swd.values.helpers;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.helpers.BaseDataFillerHelper;
import br.inpe.climaespacial.swd.values.dtos.ValueEntry;
import br.inpe.climaespacial.swd.values.factories.ValueEntryFactory;

@Dependent
public class DefaultValueEntryDataFillerHelper extends BaseDataFillerHelper<ValueEntry> implements ValueEntryDataFillerHelper {

    @Inject
    private ValueEntryFactory valueEntryFactory;
    
    @Override
    protected ValueEntry create() {
        return valueEntryFactory.create();
    }


}
