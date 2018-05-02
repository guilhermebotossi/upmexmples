package br.inpe.climaespacial.swd.calculation.factories;

import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import javax.inject.Inject;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultMagPlasmaFactory.class, MagPlasma.class})
public class MagPlasmaFactoryTest {

    @Inject
    MagPlasmaFactory magPlasmaFactory;

    @Test
    public void create_called_returnsMagPlasma() {

        MagPlasma mp = magPlasmaFactory.create();

        assertThat(mp, instanceOf(MagPlasma.class));
    }

}
