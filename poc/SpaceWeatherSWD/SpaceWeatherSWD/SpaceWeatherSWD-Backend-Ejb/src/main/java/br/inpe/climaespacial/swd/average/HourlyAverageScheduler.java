package br.inpe.climaespacial.swd.average;

import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
@DependsOn("DefaultTimeZone")
public class HourlyAverageScheduler {

    @Inject
    private Average average;

    @AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
    @Schedule(hour = "*", minute = "*", second = "10", persistent = false)
    public void hourlyAverageCalculate() {
        average.calculate();
    }

}
