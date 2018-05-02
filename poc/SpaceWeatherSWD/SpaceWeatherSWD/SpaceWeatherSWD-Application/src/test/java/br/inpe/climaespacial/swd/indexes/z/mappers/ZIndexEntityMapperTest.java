package br.inpe.climaespacial.swd.indexes.z.mappers;

import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import br.inpe.climaespacial.swd.indexes.z.factories.ZIndexEntityFactory;
import java.time.ZonedDateTime;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultZIndexEntityMapper.class})
public class ZIndexEntityMapperTest {

    @Mock
    @Produces
    private ZIndexEntityFactory zIndexEntityFactory;

    @Inject
    private ZIndexEntityMapper zIndexEntityMapper;

    @Test
    public void map_calledWithNullArgument_throwsRuntimeException() {
        RuntimeException re = null;

        try {
            zIndexEntityMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"zIndex\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_returnsZIndex() {
        ZIndex zi = new ZIndex();
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        zi.setTimeTag(timeTag);
        zi.setPreValue(5.0);
        zi.setPostValue(5.0);
        ZIndexEntity zie1 = new ZIndexEntity();

        when(zIndexEntityFactory.create()).thenReturn(zie1);

        ZIndexEntity zie2 = zIndexEntityMapper.map(zi);

        assertNotNull(zie2);
        assertEquals(zi.getTimeTag(), zie2.getTimeTag());
        assertEquals(zi.getPreValue(), zie2.getPreValue());
        assertEquals(zi.getPostValue(), zie2.getPostValue());
    }
}
