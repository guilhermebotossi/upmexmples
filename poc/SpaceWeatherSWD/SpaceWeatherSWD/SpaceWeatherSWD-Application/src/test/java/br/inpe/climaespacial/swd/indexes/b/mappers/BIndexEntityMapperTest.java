package br.inpe.climaespacial.swd.indexes.b.mappers;

import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import br.inpe.climaespacial.swd.indexes.b.factories.BIndexEntityFactory;
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
@AdditionalClasses({DefaultBIndexEntityMapper.class})
public class BIndexEntityMapperTest {

    @Mock
    @Produces
    private BIndexEntityFactory bIndexEntityFactory;

    @Inject
    private BIndexEntityMapper bIndexEntityMapper;

    @Test
    public void map_calledWithNullArgument_throwsRuntimeException() {
        RuntimeException re = null;

        try {
            bIndexEntityMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"bIndex\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_returnsCalculatedValuesEntity() {
        BIndex bi = new BIndex();
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        bi.setTimeTag(timeTag);
        bi.setPreValue(4.0);
        bi.setPostValue(5.0);
        BIndexEntity bie1 = new BIndexEntity();
        when(bIndexEntityFactory.create()).thenReturn(bie1);

        BIndexEntity bie2 = bIndexEntityMapper.map(bi);

        assertNotNull(bie2);
        assertEquals(bi.getTimeTag(), bie2.getTimeTag());
        assertEquals(bi.getPreValue(), bie2.getPreValue());
        assertEquals(bi.getPostValue(), bie2.getPostValue());
    }

}
