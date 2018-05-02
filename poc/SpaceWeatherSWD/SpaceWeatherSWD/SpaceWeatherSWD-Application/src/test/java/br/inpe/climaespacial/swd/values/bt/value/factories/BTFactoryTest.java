package br.inpe.climaespacial.swd.values.bt.value.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.values.bt.value.dtos.BT;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultBTFactory.class})
public class BTFactoryTest {

    @Inject
    private BTFactory btFactory;

    @Test
    public void create_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;
        BT bt = btFactory.create(zdt, value);

        assertNotNull(bt);
        assertSame(zdt, bt.getTimeTag());
        assertSame(value, bt.getValue());
        assertEquals(BT.class, bt.getClass());
    }

}
