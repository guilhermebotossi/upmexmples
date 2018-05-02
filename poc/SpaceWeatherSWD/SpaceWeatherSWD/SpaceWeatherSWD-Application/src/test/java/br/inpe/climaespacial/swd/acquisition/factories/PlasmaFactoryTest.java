package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultPlasmaFactory.class,
    Plasma.class
})
public class PlasmaFactoryTest {

    @Inject
    private PlasmaFactory plasmaFactory;

    @Test
    public void create_called_returnsPlasma() {
        Plasma p = plasmaFactory.create();

        assertNotNull(p);
        assertEquals(Plasma.class, p.getClass());
    }

}
