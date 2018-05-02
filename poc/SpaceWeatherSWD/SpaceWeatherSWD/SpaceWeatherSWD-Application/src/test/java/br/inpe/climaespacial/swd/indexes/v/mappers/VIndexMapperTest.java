package br.inpe.climaespacial.swd.indexes.v.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import br.inpe.climaespacial.swd.indexes.v.factories.VIndexFactory;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultVIndexMapper.class)
public class VIndexMapperTest {

    private static final double DELTA = 0.001;

    private static final double VALUE_1 = 1.0;

    private static final double VALUE_2 = 2.0;

    @Produces
    @Mock
    private ListFactory<VIndex> vIndexListFactory;

    @Produces
    @Mock
    private VIndexFactory vIndexFactory;

    @Inject
    private VIndexMapper vIndexMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            vIndexMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"vIndexEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_calledWith_returnsList() {

        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        VIndexEntity vie1 = new VIndexEntity();
        vie1.setTimeTag(zdt1);
        vie1.setPreValue(VALUE_1);
        vie1.setPostValue(VALUE_2);
        vie1.setIsCycleBegin(true);

        List<VIndexEntity> viel = Arrays.asList(vie1);

        when(vIndexListFactory.create()).thenReturn(new ArrayList<VIndex>());
        when(vIndexFactory.create()).thenReturn(new VIndex());

        List<VIndex> vil = vIndexMapper.map(viel);

        verify(vIndexListFactory, times(1)).create();
        verify(vIndexFactory, times(1)).create();

        assertNotNull(vil);
        assertEquals(1, vil.size());

        VIndex vi = vil.get(0);
        assertEquals(VALUE_1, vi.getPreValue(), DELTA);
        assertEquals(VALUE_2, vi.getPostValue(), DELTA);
        assertTrue(vi.getIsCycleBegin());
        assertEquals(vie1.getTimeTag(), vi.getTimeTag());
    }

}
