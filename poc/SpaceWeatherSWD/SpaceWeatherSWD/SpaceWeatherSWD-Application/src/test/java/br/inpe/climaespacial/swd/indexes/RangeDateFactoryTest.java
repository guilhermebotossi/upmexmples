package br.inpe.climaespacial.swd.indexes;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultRangeDateFactory.class})
public class RangeDateFactoryTest {

    @Inject
    private RangeDateFactory rangeDateFactory;

    @Test
    public void create_calledWithNullArgument_returnsRangeDateWithNullMinMax() {
        RangeDate rd = rangeDateFactory.create(null, null);

        assertNotNull(rd);
        assertEquals(RangeDate.class, rd.getClass());
        assertNull(rd.getMinDate());
        assertNull(rd.getMaxDate());
    }

    @Test
    public void create_called_returnsRangeDate() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-07T12:00:00z[UTC]");

        RangeDate rd = rangeDateFactory.create(zdt1, zdt2);

        assertNotNull(rd);
        assertEquals(RangeDate.class, rd.getClass());
        assertEquals(rd.getMinDate(), zdt1);
        assertSame(rd.getMaxDate(), zdt2);
    }

}
