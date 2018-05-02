package br.inpe.climaespacial.swd.indexes.c.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.factories.CIndexFactory;
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
@AdditionalClasses(DefaultCIndexMapper.class)
public class CIndexMapperTest {

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Produces
    @Mock
    private ListFactory<CIndex> cIndexListFactory;

    @Produces
    @Mock
    private CIndexFactory cIndexFactory;

    @Inject
    private CIndexMapper cIndexMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            cIndexMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"cIndexEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_calledWith_returnsList() {

        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        CIndexEntity ci1 = new CIndexEntity();
        ci1.setTimeTag(zdt1);
        ci1.setPreValue(VALUE);
        ci1.setPostValue(VALUE);

        List<CIndexEntity> ciel = Arrays.asList(ci1);

        when(cIndexListFactory.create()).thenReturn(new ArrayList<CIndex>());
        when(cIndexFactory.create()).thenReturn(new CIndex());

        List<CIndex> cil = cIndexMapper.map(ciel);

        verify(cIndexListFactory, times(1)).create();
        verify(cIndexFactory, times(1)).create();

        assertNotNull(cil);
        assertEquals(1, cil.size());

        CIndex ci = cil.get(0);
        assertEquals(ci1.getPreValue(), ci.getPreValue(), DELTA);
        assertEquals(ci1.getPostValue(), ci.getPostValue(), DELTA);
        assertEquals(ci1.getTimeTag(), ci.getTimeTag());
    }

}
