package br.inpe.climaespacial.swd.values.bx.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.bx.value.dtos.BX;
import br.inpe.climaespacial.swd.values.bx.value.entities.BXEntity;
import br.inpe.climaespacial.swd.values.bx.value.factories.BXFactory;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultBXMapper.class,
    DefaultListFactory.class
})
public class BXMapperTest {

    @Produces
    @Mock
    private BXFactory bxFactory;

    @Inject
    private BXMapper bxMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            bxMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"bxEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        BXEntity bxe = new BXEntity(zdt, value);
        BX bx1 = new BX();
        bx1.setTimeTag(zdt);
        bx1.setValue(value);
        List<BXEntity> bxel = Arrays.asList(bxe);

        when(bxFactory.create(zdt, value)).thenReturn(bx1);

        List<BX> bxl = bxMapper.map(bxel);

        verify(bxFactory).create(zdt, value);
        assertNotNull(bxl);
        assertThat(bxl, is(not(empty())));
        assertEquals(bxel.size(), bxl.size());
        BX bx2 = bxl.get(0);
        assertEquals(zdt, bx2.getTimeTag());
        assertEquals(value, bx2.getValue());
    }
}
