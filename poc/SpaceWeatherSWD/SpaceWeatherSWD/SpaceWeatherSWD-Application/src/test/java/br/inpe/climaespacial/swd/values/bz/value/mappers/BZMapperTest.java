package br.inpe.climaespacial.swd.values.bz.value.mappers;

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
import br.inpe.climaespacial.swd.values.bz.value.dtos.BZ;
import br.inpe.climaespacial.swd.values.bz.value.entities.BZEntity;
import br.inpe.climaespacial.swd.values.bz.value.factories.BZFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultBZMapper.class,
					DefaultListFactory.class
	})
public class BZMapperTest {
	
    @Produces
    @Mock
    private BZFactory bzFactory;
	
	@Inject
	private BZMapper bzMapper;

	@Test
	public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
        	bzMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Parâmetro \"bzEntityList\" null.", re.getMessage());
	}
	
	@Test
	public void map_called_succeeds() {
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
		Double value = 2.0;
		
		BZEntity bze = new BZEntity(zdt, value);
		BZ bz1 = new BZ();
		bz1.setTimeTag(zdt);
		bz1.setValue(value);
		List<BZEntity> bzel = Arrays.asList(bze);
		
		when(bzFactory.create(zdt, value)).thenReturn(bz1);
		
		List<BZ> bzl = bzMapper.map(bzel);
		
		verify(bzFactory).create(zdt, value); 
		assertNotNull(bzl);
		assertThat(bzl, is(not(empty())));
		assertEquals(bzel.size(), bzl.size());
		BZ bz2 = bzl.get(0);
		assertEquals(zdt, bz2.getTimeTag());
		assertEquals(value, bz2.getValue());
	}
}
