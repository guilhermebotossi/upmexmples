package br.inpe.climaespacial.swd.indexes;

import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.indexes.z.services.ZIndexService;

@Startup
@Singleton
@DependsOn("DefaultTimeZone")
public class ZIndexScheduler {

    @Inject
    private ZIndexService zIndexService;

    @AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
    @Schedule(hour = "*", minute = "*", second = "40", persistent = false)
    public void zIndexCalculate() {
        zIndexService.calculate();
    }

}
