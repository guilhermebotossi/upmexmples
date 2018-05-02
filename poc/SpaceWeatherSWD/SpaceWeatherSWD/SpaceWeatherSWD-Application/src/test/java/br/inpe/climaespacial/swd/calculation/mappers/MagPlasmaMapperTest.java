package br.inpe.climaespacial.swd.calculation.mappers;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;
import br.inpe.climaespacial.swd.calculation.entities.MagPlasmaEntity;
import br.inpe.climaespacial.swd.calculation.factories.MagPlasmaFactory;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
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
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultMagPlasmaMapper.class, MagPlasma.class})
public class MagPlasmaMapperTest {

    @Produces
    @Mock
    private MagPlasmaFactory magPlasmaFactory;

    @Produces
    @Mock
    private ListFactory<MagPlasma> magPlasmaListFactory;

    @Inject
    private MagPlasmaMapper magPlasmaMapper;

    @Test
    public void map_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            magPlasmaMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"magPlasmaEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_returnsMagPlasma() {

        Double expectedBzGsm = 1.0;
        Double expectedSpeed = 2.0;
        Double expectedDensity = 3.0;
        ZonedDateTime expectedZdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        when(magPlasmaListFactory.create()).thenReturn(new ArrayList<>());
        MagPlasmaEntity mpe1 = mock(MagPlasmaEntity.class);
        List<MagPlasmaEntity> mpel = Arrays.asList(mpe1);

        MagEntity me1 = mock(MagEntity.class);
        when(me1.getBzGsm()).thenReturn(expectedBzGsm);
        when(me1.getTimeTag()).thenReturn(expectedZdt);
        when(mpe1.getMagEntity()).thenReturn(me1);

        PlasmaEntity pe1 = mock(PlasmaEntity.class);
        when(pe1.getSpeed()).thenReturn(expectedSpeed);
        when(pe1.getDensity()).thenReturn(expectedDensity);
        when(mpe1.getPlasmaEntity()).thenReturn(pe1);

        when(magPlasmaFactory.create()).thenReturn(new MagPlasma());

        List<MagPlasma> mpl = magPlasmaMapper.map(mpel);

        assertNotNull(mpl);
        assertEquals(1, mpl.size());

        MagPlasma mp = mpl.get(0);
        assertSame(expectedBzGsm, mp.getBzGsm());
        assertSame(expectedSpeed, mp.getSpeed());
        assertSame(expectedDensity, mp.getDensity());
        assertSame(expectedZdt, mp.getTimeTag());
    }
}
