package br.inpe.climaespacial.swd.acquisition.mappers;

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.calculation.factories.PlasmaEntityFactory;
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
@AdditionalClasses({DefaultPlasmaEntityMapper.class})
public class PlasmaEntityMapperTest {

    @Produces
    @Mock
    private ListFactory<PlasmaEntity> plasmaEntityListFactory;

    @Produces
    @Mock
    private PlasmaEntityFactory plasmaEntityFactory;

    @Inject
    PlasmaEntityMapper plasmaEntityMapper;

    @Test
    public void map_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            plasmaEntityMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"plasmaList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_map_returnsList() {

        Plasma p1 = mock(Plasma.class);
        List<Plasma> pl = Arrays.asList(p1);
        LocalDateTime firstDay_2014 = LocalDateTime.of(2014, Month.JANUARY, 1, 1, 1);
        ZonedDateTime firstDay_2014_zoned = ZonedDateTime.of(firstDay_2014, ZoneId.of("UTC"));

        when(p1.getTimeTag()).thenReturn(firstDay_2014_zoned);
        when(p1.getDensity()).thenReturn(1.0);
        when(p1.getSpeed()).thenReturn(2.0);
        when(p1.getTemperature()).thenReturn(3.0);

        when(plasmaEntityListFactory.create()).thenReturn(new ArrayList<>());
        when(plasmaEntityFactory.create()).thenReturn(new PlasmaEntity());

        List<PlasmaEntity> pel = plasmaEntityMapper.map(pl);

        assertNotNull(pel);
        assertEquals(1, pel.size());

        PlasmaEntity pe1 = pel.get(0);
        double delta = 0.001;

        assertEquals(p1.getTimeTag(), pe1.getTimeTag());
        assertEquals(p1.getDensity(), pe1.getDensity(), delta);
        assertEquals(p1.getSpeed(), pe1.getSpeed(), delta);
        assertEquals(p1.getTemperature(), pe1.getTemperature(), delta);
    }

}
