package br.inpe.climaespacial.swd.acquisition.mappers;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.factories.MagEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultMagEntityMapper.class})
public class MagEntityMapperTest {

    @Produces
    @Mock
    private ListFactory<MagEntity> magEntityListFactory;

    @Produces
    @Mock
    private MagEntityFactory magEntityFactory;

    @Inject
    private MagEntityMapper magEntityMapper;

    @Test
    public void map_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            magEntityMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"magList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_map_returnsList() {
        Mag m1 = mock(Mag.class);
        List<Mag> ml = Arrays.asList(m1);

        LocalDateTime firstDay_2014 = LocalDateTime.of(2014, Month.JANUARY, 1, 1, 1);
        ZonedDateTime firstDay_2014_zoned = ZonedDateTime.of(firstDay_2014, ZoneId.of("UTC"));

        when(m1.getTimeTag()).thenReturn(firstDay_2014_zoned);
        when(m1.getBxGsm()).thenReturn(1.0d);
        when(m1.getByGsm()).thenReturn(2.0d);
        when(m1.getBzGsm()).thenReturn(3.0d);
        when(m1.getLatGsm()).thenReturn(4.0d);
        when(m1.getLonGsm()).thenReturn(5.0d);
        when(m1.getBt()).thenReturn(6.0d);

        when(magEntityListFactory.create()).thenReturn(new ArrayList<>());
        when(magEntityFactory.create()).thenReturn(new MagEntity());

        List<MagEntity> mel = magEntityMapper.map(ml);

        assertNotNull(mel);
        assertEquals(1, mel.size());

        MagEntity me1 = mel.get(0);
        double delta = 0.001;

        assertEquals(m1.getTimeTag(), me1.getTimeTag());
        assertEquals(m1.getBxGsm(), me1.getBxGsm(), delta);
        assertEquals(m1.getByGsm(), me1.getByGsm(), delta);
        assertEquals(m1.getBzGsm(), me1.getBzGsm(), delta);
        assertEquals(m1.getLatGsm(), me1.getLatGsm(), delta);
        assertEquals(m1.getLonGsm(), me1.getLonGsm(), delta);
        assertEquals(m1.getBt(), me1.getBt(), delta);
    }
}
