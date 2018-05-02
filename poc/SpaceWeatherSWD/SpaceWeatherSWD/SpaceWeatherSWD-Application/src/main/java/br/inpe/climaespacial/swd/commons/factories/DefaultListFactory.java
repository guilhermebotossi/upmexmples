package br.inpe.climaespacial.swd.commons.factories;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class DefaultListFactory<I> implements ListFactory<I> {

    @Override
    public List<I> create() {
        return new ArrayList<>();
    }

}
