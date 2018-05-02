package br.inpe.climaespacial.swd.values.density.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.density.value.dtos.Density;
import br.inpe.climaespacial.swd.values.density.value.entities.DensityEntity;
import br.inpe.climaespacial.swd.values.density.value.factories.DensityFactory;
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
@AdditionalClasses({DefaultDensityMapper.class,
    DefaultListFactory.class
})
public class DensityMapperTest {

    @Produces
    @Mock
    private DensityFactory densityFactory;

    @Inject
    private DensityMapper densityMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            densityMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"densityEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        DensityEntity densitye = new DensityEntity(zdt, value);
        Density density1 = new Density();
        density1.setTimeTag(zdt);
        density1.setValue(value);
        List<DensityEntity> densityel = Arrays.asList(densitye);

        when(densityFactory.create(zdt, value)).thenReturn(density1);

        List<Density> densityl = densityMapper.map(densityel);

        verify(densityFactory).create(zdt, value);
        assertNotNull(densityl);
        assertThat(densityl, is(not(empty())));
        assertEquals(densityel.size(), densityl.size());
        Density density2 = densityl.get(0);
        assertEquals(zdt, density2.getTimeTag());
        assertEquals(value, density2.getValue());
    }
}
