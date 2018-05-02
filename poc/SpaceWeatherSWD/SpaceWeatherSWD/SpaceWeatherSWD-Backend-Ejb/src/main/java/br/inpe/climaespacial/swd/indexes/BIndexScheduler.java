package br.inpe.climaespacial.swd.indexes;

import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.indexes.b.services.BIndexService;

@Startup
@Singleton
@DependsOn("DefaultTimeZone")
public class BIndexScheduler {

    @Inject
    private BIndexService bIndexService;

    @AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
    @Schedule(hour = "*", minute = "*", second = "20", persistent = false)
    public void bIndexCalculate() {
        bIndexService.calculate();
    }

}
