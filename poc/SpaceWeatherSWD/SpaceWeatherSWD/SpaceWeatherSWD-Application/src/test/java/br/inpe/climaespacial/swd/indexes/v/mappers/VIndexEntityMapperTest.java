package br.inpe.climaespacial.swd.indexes.v.mappers;

import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import br.inpe.climaespacial.swd.indexes.v.factories.VIndexEntityFactory;
import java.time.ZonedDateTime;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultVIndexEntityMapper.class})
public class VIndexEntityMapperTest {

    private static final double DELTA = 0.001;

    private static final double VALUE_1 = 1.0;

    private static final double VALUE_2 = 2.0;

    @Mock
    @Produces
    private VIndexEntityFactory vIndexEntityFactory;

    @Inject
    private VIndexEntityMapper vIndexEntityMapper;

    @Test
    public void map_calledWithNullArgument_throwsRuntimeException() {
        RuntimeException re = null;

        try {
            vIndexEntityMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"vIndex\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_returnsVIndex() {
        VIndex vi = new VIndex();
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        vi.setTimeTag(timeTag);
        vi.setPreValue(VALUE_1);
        vi.setPostValue(VALUE_2);
        vi.setIsCycleBegin(true);
        VIndexEntity vie1 = new VIndexEntity();

        when(vIndexEntityFactory.create()).thenReturn(vie1);

        VIndexEntity vie2 = vIndexEntityMapper.map(vi);

        assertNotNull(vie2);
        assertEquals(timeTag, vie2.getTimeTag());
        assertEquals(VALUE_1, vie2.getPreValue(), DELTA);
        assertEquals(VALUE_2, vie2.getPostValue(), DELTA);
        assertTrue(vie2.getIsCycleBegin());
    }

}
