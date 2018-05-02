package br.inpe.climaespacial.swd.indexes.c.factories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.UUIDProvider;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
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
@AdditionalClasses({HelperFactoryProducer.class, DefaultCIndexEntityFactory.class, CIndexEntity.class})
public class CIndexEntityFactoryTest {

    @Produces
    @Mock
    private UUIDProvider uuidProvider;

    @Produces
    @Mock
    private DateTimeProvider dateTimeProvider;

    @Inject
    private CIndexEntityFactory cIndexEntityFactory;

    @Test
    public void create_called_returnsCIndexEntity() {
        CIndexEntity cie = cIndexEntityFactory.create();

        assertNotNull(cie);
        assertEquals(CIndexEntity.class, cie.getClass());
    }

}
