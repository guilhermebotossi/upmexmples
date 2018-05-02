package br.inpe.climaespacial.swd.calculation.factories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.UUIDProvider;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultCalculatedValuesEntityFactory.class, CalculatedValuesEntity.class})
public class CalculatedValuesEntityFactoryTest {

    @Produces
    @Mock
    private UUIDProvider uuidProvider;

    @Produces
    @Mock
    private DateTimeProvider dateTimeProvider;

    @Inject
    private CalculatedValuesEntityFactory calculatedValuesEntityFactory;

    @Test
    public void create_called_returnsCalculatedValuesEntity() {
        CalculatedValuesEntity cve = calculatedValuesEntityFactory.create();

        assertNotNull(cve);
        assertThat(cve, instanceOf(CalculatedValuesEntity.class));
    }

}
