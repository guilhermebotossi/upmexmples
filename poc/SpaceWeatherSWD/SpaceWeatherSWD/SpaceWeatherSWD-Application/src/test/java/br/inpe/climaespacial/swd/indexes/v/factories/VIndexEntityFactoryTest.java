package br.inpe.climaespacial.swd.indexes.v.factories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.UUIDProvider;
import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
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
@AdditionalClasses({HelperFactoryProducer.class, DefaultVIndexEntityFactory.class, VIndexEntity.class})
public class VIndexEntityFactoryTest {

    @Produces
    @Mock
    private UUIDProvider uuidProvider;

    @Produces
    @Mock
    private DateTimeProvider dateTimeProvider;

    @Inject
    private VIndexEntityFactory vIndexEntityFactory;

    @Test
    public void create_called_returnsVIndexEntity() {
        VIndexEntity cie = vIndexEntityFactory.create();

        assertNotNull(cie);
        assertEquals(VIndexEntity.class, cie.getClass());
    }

}
