package br.inpe.climaespacial.swd.indexes.b.factories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.UUIDProvider;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultBIndexEntityFactory.class, BIndexEntity.class})
public class BIndexEntityFactoryTest {

    @Produces
    @Mock
    private UUIDProvider uuidProvider;

    @Produces
    @Mock
    private DateTimeProvider dateTimeProvider;

    @Inject
    private BIndexEntityFactory bIndexEntityFactory;

    @Test
    public void create_called_returnsBIndexEntity() {
        BIndexEntity bie = bIndexEntityFactory.create();

        assertNotNull(bie);
        assertEquals(BIndexEntity.class, bie.getClass());
    }

}
