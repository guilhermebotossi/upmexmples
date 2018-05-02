package br.inpe.climaespacial.swd.indexes.v.helpers;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.helpers.BaseDataFillerHelper;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.factories.VIndexFactory;

@Dependent
public class DefaultVIndexDataFillerHelper extends BaseDataFillerHelper<VIndex> implements VIndexDataFillerHelper {

    @Inject
    private VIndexFactory vIndexFactory;

    @Override
    protected VIndex create() {
        return vIndexFactory.create();
    }
    
//    @Override
//    public VIndex createTimeTagable() {
//        return vIndexFactory.create();
//    }

}
