package br.inpe.climaespacial.swd.indexes;

import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.indexes.c.services.CIndexService;

@Startup
@Singleton
@DependsOn("DefaultTimeZone")
public class CIndexScheduler {

    @Inject
    private CIndexService cIndexService;

    @AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
    @Schedule(hour = "*", minute = "*", second = "30", persistent = false)
    public void cIndexCalculate() {
        cIndexService.calculate();
    }

}
