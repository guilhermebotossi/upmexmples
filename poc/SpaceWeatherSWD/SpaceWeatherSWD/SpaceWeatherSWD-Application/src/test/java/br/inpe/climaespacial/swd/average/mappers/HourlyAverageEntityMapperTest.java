package br.inpe.climaespacial.swd.average.mappers;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.average.factories.HourlyAverageEntityFactory;
import java.time.ZonedDateTime;
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
@AdditionalClasses(DefaultHourlyAverageEntityMapper.class)
public class HourlyAverageEntityMapperTest {

    @Produces
    @Mock
    private HourlyAverageEntityFactory hourlyAverageEntityFactory;

    @Inject
    private HourlyAverageEntityMapper hourlyAverageEntityMapper;

    @Test
    public void map_calledWithNullArgument_throwsRuntimeException() {
        RuntimeException re = null;
        try {
            hourlyAverageEntityMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }
        assertNotNull(re);
        assertEquals("Argument \"hourlyAverage\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_returnsCalculatedValuesEntity() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        double bxGsm = 1.0;
        double byGsm = 2.0;
        double bzGsm = 3.0;
        double bt = 4.0;
        double density = 5.0;
        double temperature = 6.0;
        double speed = 7.0;
        double ey = 8.0;
        double dpr = 9.0;
        double rmp = 10.0;

        double delta = 0.0001;

        HourlyAverage ha = createHourlyAverage(timeTag, bxGsm, byGsm, bzGsm, bt, density, temperature, speed, ey, dpr, rmp);

        HourlyAverageEntity hae = new HourlyAverageEntity();

        when(hourlyAverageEntityFactory.create()).thenReturn(hae);

        HourlyAverageEntity hae1 = hourlyAverageEntityMapper.map(ha);

        assertNotNull(hae1);
        assertEquals(timeTag, hae1.getTimeTag());
        assertEquals(bxGsm, hae1.getBxGsm(), delta);
        assertEquals(byGsm, hae1.getByGsm(), delta);
        assertEquals(bzGsm, hae1.getBzGsm(), delta);
        assertEquals(bt, hae1.getBt(), delta);
        assertEquals(density, hae1.getDensity(), delta);
        assertEquals(temperature, hae1.getTemperature(), delta);
        assertEquals(speed, hae1.getSpeed(), delta);
        assertEquals(ey, hae1.getEy(), delta);
        assertEquals(dpr, hae1.getDpr(), delta);
        assertEquals(rmp, hae1.getRmp(), delta);
    }

    private HourlyAverage createHourlyAverage(ZonedDateTime timeTag, double bxGsm, double byGsm, double bzGsm, double bt, double density, double temperature, double speed, double ey, double dpr, double rmp) {
        HourlyAverage ha = new HourlyAverage();
        ha.setTimeTag(timeTag);
        ha.setBxGsm(bxGsm);
        ha.setByGsm(byGsm);
        ha.setBzGsm(bzGsm);
        ha.setBt(bt);
        ha.setDensity(density);
        ha.setTemperature(temperature);
        ha.setSpeed(speed);
        ha.setEy(ey);
        ha.setDpr(dpr);
        ha.setRmp(rmp);
        return ha;
    }
}
