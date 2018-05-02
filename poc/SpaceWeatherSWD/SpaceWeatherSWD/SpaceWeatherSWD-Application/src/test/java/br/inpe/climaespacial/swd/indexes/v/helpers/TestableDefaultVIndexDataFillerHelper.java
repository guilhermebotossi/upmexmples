package br.inpe.climaespacial.swd.indexes.v.helpers;

import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;

public class TestableDefaultVIndexDataFillerHelper extends DefaultVIndexDataFillerHelper {

    public VIndex createPublic() {
        return super.create();
    }
}
