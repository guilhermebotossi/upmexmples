package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.calculation.factories.PlasmaEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.UUIDProvider;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultPlasmaEntityFactory.class, PlasmaEntity.class})
public class PlasmaEntityFactoryTest {

    @Produces
    @Mock
    private UUIDProvider uuidProvider;

    @Produces
    @Mock
    private DateTimeProvider dateTimeProvider;

    @Inject
    private PlasmaEntityFactory plasmaEntityFactory;

    @Test
    public void create_called_returnsPlasmaEntity() {
        PlasmaEntity pe = plasmaEntityFactory.create();

        assertThat(pe, instanceOf(PlasmaEntity.class));
    }

}
