package br.inpe.climaespacial.swd.indexes.c.mappers;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.factories.CIndexEntityFactory;
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
@AdditionalClasses({DefaultCIndexEntityMapper.class})
public class CIndexEntityMapperTest {

    @Mock
    @Produces
    private CIndexEntityFactory cIndexEntityFactory;

    @Inject
    private CIndexEntityMapper cIndexEntityMapper;

    @Test
    public void map_calledWithNullArgument_throwsRuntimeException() {
        RuntimeException re = null;

        try {
            cIndexEntityMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"cIndex\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_returnsCIndex() {
        CIndex ci = new CIndex();
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ci.setTimeTag(timeTag);
        ci.setPreValue(5.0);
        ci.setPostValue(5.0);
        CIndexEntity cie1 = new CIndexEntity();

        when(cIndexEntityFactory.create()).thenReturn(cie1);

        CIndexEntity cie2 = cIndexEntityMapper.map(ci);

        assertNotNull(cie2);
        assertEquals(ci.getTimeTag(), cie2.getTimeTag());
        assertEquals(ci.getPreValue(), cie2.getPreValue());
        assertEquals(ci.getPostValue(), cie2.getPostValue());
    }

}
