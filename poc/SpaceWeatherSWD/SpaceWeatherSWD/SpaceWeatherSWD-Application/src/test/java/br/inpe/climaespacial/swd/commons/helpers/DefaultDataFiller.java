package br.inpe.climaespacial.swd.commons.helpers;

import java.io.Serializable;

import br.inpe.climaespacial.swd.indexes.v.helpers.DefaultTimeTagable;

public class DefaultDataFiller extends BaseDataFillerHelper<TimeTagable> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public TimeTagable create() {
        return new DefaultTimeTagable();
    }
}
