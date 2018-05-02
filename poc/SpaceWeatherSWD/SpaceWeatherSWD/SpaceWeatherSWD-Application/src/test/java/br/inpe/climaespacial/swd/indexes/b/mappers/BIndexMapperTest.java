package br.inpe.climaespacial.swd.indexes.b.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import br.inpe.climaespacial.swd.indexes.b.factories.BIndexFactory;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultBIndexMapper.class)
public class BIndexMapperTest {

    private static final double DELTA = 0.00;

    private static final double PRE_VALUE = 1.0;

    private static final double POST_VALUE = 2.0;

    @Produces
    @Mock
    private ListFactory<BIndex> bIndexListFactory;

    @Produces
    @Mock
    private BIndexFactory bIndexFactory;

    @Inject
    private BIndexMapper bIndexMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            bIndexMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"bIndexEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_calledWith_returnsList() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        BIndexEntity bi1 = new BIndexEntity();
        bi1.setTimeTag(zdt1);
        bi1.setPreValue(PRE_VALUE);
        bi1.setPostValue(POST_VALUE);

        List<BIndexEntity> biel = Arrays.asList(bi1);

        when(bIndexListFactory.create()).thenReturn(new ArrayList<BIndex>());
        when(bIndexFactory.create()).thenReturn(new BIndex());

        List<BIndex> bil = bIndexMapper.map(biel);

        verify(bIndexListFactory, times(1)).create();
        verify(bIndexFactory, times(1)).create();

        assertNotNull(bil);
        assertEquals(1, bil.size());

        BIndex bi = bil.get(0);
        assertEquals(bi1.getPreValue(), bi.getPreValue(), DELTA);
        assertEquals(bi1.getPostValue(), bi.getPostValue(), DELTA);
        assertEquals(bi1.getTimeTag(), bi.getTimeTag());
    }
}
