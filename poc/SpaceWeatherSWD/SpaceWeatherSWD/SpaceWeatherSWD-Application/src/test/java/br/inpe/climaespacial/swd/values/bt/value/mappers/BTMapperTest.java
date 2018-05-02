package br.inpe.climaespacial.swd.values.bt.value.mappers;

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
import br.inpe.climaespacial.swd.values.bt.value.dtos.BT;
import br.inpe.climaespacial.swd.values.bt.value.entities.BTEntity;
import br.inpe.climaespacial.swd.values.bt.value.factories.BTFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultBTMapper.class,
					DefaultListFactory.class
	})
public class BTMapperTest {
	
    @Produces
    @Mock
    private BTFactory btFactory;
	
	@Inject
	private BTMapper btMapper;

	@Test
	public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
        	btMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"btEntityList\" cannot be null.", re.getMessage());
	}
	
	@Test
	public void map_called_succeeds() {
		ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
		Double value = 2.0;
		
		BTEntity bte = new BTEntity(zdt, value);
		BT bt1 = new BT();
		bt1.setTimeTag(zdt);
		bt1.setValue(value);
		List<BTEntity> btel = Arrays.asList(bte);
		
		when(btFactory.create(zdt, value)).thenReturn(bt1);
		
		List<BT> btl = btMapper.map(btel);
		
		verify(btFactory).create(zdt, value); 
		assertNotNull(btl);
		assertThat(btl, is(not(empty())));
		assertEquals(btel.size(), btl.size());
		BT bt2 = btl.get(0);
		assertEquals(zdt, bt2.getTimeTag());
		assertEquals(value, bt2.getValue());
	}
}
