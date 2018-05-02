package br.inpe.climaespacial.swd.values.bz.average.mappers;

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
import br.inpe.climaespacial.swd.values.bz.average.dtos.AverageBZ;
import br.inpe.climaespacial.swd.values.bz.average.entities.AverageBZEntity;
import br.inpe.climaespacial.swd.values.bz.average.factories.AverageBZFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultAverageBZMapper.class,
					DefaultListFactory.class
	})
public class AverageBZMapperTest {
	
    @Produces
    @Mock
    private AverageBZFactory averageBZFactory;
	
	@Inject
	private AverageBZMapper averageBZMapper;

	@Test
	public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
        	averageBZMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Parâmetro \"averageBZEntityList\" null.", re.getMessage());
	}
	
	@Test
	public void map_called_succeeds() {
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
		Double value = 2.0;
		
		AverageBZEntity averageBZe = new AverageBZEntity(zdt, value);
		AverageBZ averageBZ1 = new AverageBZ();
		averageBZ1.setTimeTag(zdt);
		averageBZ1.setValue(value);
		List<AverageBZEntity> averageBZel = Arrays.asList(averageBZe);
		
		when(averageBZFactory.create(zdt, value)).thenReturn(averageBZ1);
		
		List<AverageBZ> averageBZl = averageBZMapper.map(averageBZel);
		
		verify(averageBZFactory).create(zdt, value); 
		assertNotNull(averageBZl);
		assertThat(averageBZl, is(not(empty())));
		assertEquals(averageBZel.size(), averageBZl.size());
		AverageBZ averageBZ2 = averageBZl.get(0);
		assertEquals(zdt, averageBZ2.getTimeTag());
		assertEquals(value, averageBZ2.getValue());
	}
}
