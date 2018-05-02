package br.inpe.climaespacial.swd.values.dpr.value.mappers;

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
import br.inpe.climaespacial.swd.values.dpr.value.dtos.DPR;
import br.inpe.climaespacial.swd.values.dpr.value.entities.DPREntity;
import br.inpe.climaespacial.swd.values.dpr.value.factories.DPRFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultDPRMapper.class,
					DefaultListFactory.class
	})
public class DPRMapperTest {
	
    @Produces
    @Mock
    private DPRFactory dprFactory;
	
	@Inject
	private DPRMapper dprMapper;

	@Test
	public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
        	dprMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Parâmetro \"dprEntityList\" null.", re.getMessage());
	}
	
	@Test
	public void map_called_succeeds() {
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
		Double value = 2.0;
		
		DPREntity dpre = new DPREntity(zdt, value);
		DPR dpr1 = new DPR();
		dpr1.setTimeTag(zdt);
		dpr1.setValue(value);
		List<DPREntity> dprel = Arrays.asList(dpre);
		
		when(dprFactory.create(zdt, value)).thenReturn(dpr1);
		
		List<DPR> dprl = dprMapper.map(dprel);
		
		verify(dprFactory).create(zdt, value); 
		assertNotNull(dprl);
		assertThat(dprl, is(not(empty())));
		assertEquals(dprel.size(), dprl.size());
		DPR dpr2 = dprl.get(0);
		assertEquals(zdt, dpr2.getTimeTag());
		assertEquals(value, dpr2.getValue());
	}
}
