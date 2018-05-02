package br.inpe.climaespacial.swd.average.factories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
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
@AdditionalClasses({HelperFactoryProducer.class, DefaultHourlyAverageEntityFactory.class, HourlyAverageEntity.class})
public class HourlyAverageEntityFactoryTest {

    @Produces
    @Mock
    private UUIDProvider uuidProvider;

    @Produces
    @Mock
    private DateTimeProvider dateTimeProvider;

    @Inject
    private HourlyAverageEntityFactory hourlyAverageEntityFactory;

    @Test
    public void create_called_returnsHourlyAverageEntity() {
        HourlyAverageEntity hae = hourlyAverageEntityFactory.create();

        assertThat(hae, instanceOf(HourlyAverageEntity.class));
    }

}
