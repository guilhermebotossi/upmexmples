package br.inpe.climaespacial.swd.calculation.mappers;

import br.inpe.climaespacial.swd.calculation.dtos.CalculatedValues;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.calculation.factories.CalculatedValuesEntityFactory;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultCalculatedValuesEntityMapper.class)
public class CalculatedValuesEntityMapperTest {

    @Produces
    @Mock
    private CalculatedValuesEntityFactory calculatedValuesEntityFactory;

    @Produces
    @Mock
    private ListFactory<CalculatedValuesEntity> calculatedValuesEntityListFactory;

    @Inject
    private CalculatedValuesEntityMapper calculatedValuesEntityMapper;

    @Test
    public void map_calledWithNullArgument_throwsRuntimeException() {
        RuntimeException re = null;
        try {
            calculatedValuesEntityMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }
        assertNotNull(re);
        assertEquals("Argument \"calculatedValuesList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_returnsCalculatedValuesEntity() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        double ey = 1.0;
        double dpr = 2.0;
        double rmp = 3.0;
        double delta = 0.0001;

        CalculatedValues cv = new CalculatedValues();
        List<CalculatedValues> cvl = Arrays.asList(cv);
        CalculatedValuesEntity cve = new CalculatedValuesEntity();
        cv.setTimeTag(timeTag);
        cv.setEy(ey);
        cv.setDpr(dpr);
        cv.setRmp(rmp);

        when(calculatedValuesEntityListFactory.create()).thenReturn(new ArrayList<>());
        when(calculatedValuesEntityFactory.create()).thenReturn(cve);

        List<CalculatedValuesEntity> cvel = calculatedValuesEntityMapper.map(cvl);

        assertNotNull(cvl);
        assertEquals(1, cvel.size());

        CalculatedValuesEntity cve1 = cvel.get(0);

        assertEquals(timeTag, cve1.getTimeTag());
        assertEquals(ey, cve1.getEy(), delta);
        assertEquals(dpr, cve1.getDpr(), delta);
        assertEquals(rmp, cve1.getRmp(), delta);

    }

}
