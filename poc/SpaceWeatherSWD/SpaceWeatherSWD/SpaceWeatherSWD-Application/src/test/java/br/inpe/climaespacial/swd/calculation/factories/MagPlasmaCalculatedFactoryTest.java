package br.inpe.climaespacial.swd.calculation.factories;

import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultMagPlasmaCalculatedFactory.class, MagPlasmaCalculated.class})
public class MagPlasmaCalculatedFactoryTest {

    @Inject
    MagPlasmaCalculatedFactory magPlasmaCalculatedFactory;

    @Test
    public void create_called_returnsMagPlasmaCalculated() {
        MagPlasmaCalculated mpc = magPlasmaCalculatedFactory.create();

        assertNotNull(mpc);
        assertEquals(MagPlasmaCalculated.class, mpc.getClass());
    }

}
