package br.inpe.climaespacial.swd.kp;

import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.kp.forecast.services.KPForecastService;

@Startup
@Singleton
@DependsOn("DefaultTimeZone")
public class KpForecastScheduler {
    
    @Inject
    private KPForecastService kpForecastService;
    
    @AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
    @Schedule(hour = "*", minute = "*", persistent = false)
    public void calculate() {
        kpForecastService.calculate();
    }
}
