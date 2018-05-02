package br.inpe.climaespacial.swd.kp.forecast.factories;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.UUIDProvider;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, 
    DefaultKPForecastEntityFactory.class, 
    KPForecastEntity.class})
public class KPForecastEntityFactoryTest {
    @Produces
    @Mock
    private UUIDProvider uuidProvider;

    @Produces
    @Mock
    private DateTimeProvider dateTimeProvider;

    @Inject
    private KPForecastEntityFactory kpForecastEntityFactory; 

    @Test
    public void create_called_returnsCalculatedValuesEntity() {
        KPForecastEntity kpfe = kpForecastEntityFactory.create();

        assertNotNull(kpfe);
        assertThat(kpfe, instanceOf(KPForecastEntity.class));
    }
}
