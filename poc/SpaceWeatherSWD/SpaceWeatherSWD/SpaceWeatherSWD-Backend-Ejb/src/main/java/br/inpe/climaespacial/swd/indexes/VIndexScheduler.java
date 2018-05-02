package br.inpe.climaespacial.swd.indexes;

import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.indexes.v.services.VIndexService;

@Startup
@Singleton
@DependsOn("DefaultTimeZone")
public class VIndexScheduler {

    @Inject
    private VIndexService vIndexService;

    @AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
    @Schedule(hour = "*", minute = "*", second = "50", persistent = false)
    public void vIndexCalculate() {
        vIndexService.calculate();
    }
}
