package br.inpe.climaespacial.swd.indexes.z.factories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.UUIDProvider;
import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
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
@AdditionalClasses({HelperFactoryProducer.class, DefaultZIndexEntityFactory.class, ZIndexEntity.class})
public class ZIndexEntityFactoryTest {

    @Produces
    @Mock
    private UUIDProvider uuidProvider;

    @Produces
    @Mock
    private DateTimeProvider dateTimeProvider;

    @Inject
    private ZIndexEntityFactory zIndexEntityFactory;

    @Test
    public void create_called_returnsZIndexEntity() {
        ZIndexEntity zie = zIndexEntityFactory.create();

        assertNotNull(zie);
        assertEquals(ZIndexEntity.class, zie.getClass());
    }

}
