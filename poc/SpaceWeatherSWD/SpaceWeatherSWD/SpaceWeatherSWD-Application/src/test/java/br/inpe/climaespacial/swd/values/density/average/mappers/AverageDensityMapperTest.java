package br.inpe.climaespacial.swd.values.density.average.mappers;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.density.average.dtos.AverageDensity;
import br.inpe.climaespacial.swd.values.density.average.entities.AverageDensityEntity;
import br.inpe.climaespacial.swd.values.density.average.factories.AverageDensityFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultAverageDensityMapper.class,
					DefaultListFactory.class
	})
public class AverageDensityMapperTest {
	
    @Produces
    @Mock
    private AverageDensityFactory averageDensityFactory;
	
	@Inject
	private AverageDensityMapper averageDensityMapper;

	@Test
	public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
        	averageDensityMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Parâmetro \"averageDensityEntityList\" null.", re.getMessage());
	}
	
	@Test
	public void map_called_succeeds() {
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
		Double value = 2.0;
		
		AverageDensityEntity averageDensitye = new AverageDensityEntity(zdt, value);
		AverageDensity averageDensity1 = new AverageDensity();
		averageDensity1.setTimeTag(zdt);
		averageDensity1.setValue(value);
		List<AverageDensityEntity> averageDensityel = Arrays.asList(averageDensitye);
		
		when(averageDensityFactory.create(zdt, value)).thenReturn(averageDensity1);
		
		List<AverageDensity> averageDensityl = averageDensityMapper.map(averageDensityel);
		
		verify(averageDensityFactory).create(zdt, value); 
		assertNotNull(averageDensityl);
		assertThat(averageDensityl, is(not(empty())));
		assertEquals(averageDensityel.size(), averageDensityl.size());
		AverageDensity averageDensity2 = averageDensityl.get(0);
		assertEquals(zdt, averageDensity2.getTimeTag());
		assertEquals(value, averageDensity2.getValue());
	}
}
