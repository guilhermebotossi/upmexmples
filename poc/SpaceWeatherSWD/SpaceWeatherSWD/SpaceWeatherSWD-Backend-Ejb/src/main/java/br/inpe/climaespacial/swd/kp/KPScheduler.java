package br.inpe.climaespacial.swd.kp;

import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.TransactionTimeout;

import br.inpe.climaespacial.swd.kp.acquisition.services.KPService;

@Startup
@Singleton
@DependsOn("DefaultTimeZone")
public class KPScheduler {

    @Inject
    private KPService kpService;

    @AccessTimeout(value = 10, unit = TimeUnit.MINUTES)
    @TransactionTimeout(value = 10, unit = TimeUnit.MINUTES)
    @Schedule(hour = "*", minute = "*", second = "0", persistent = false)
    public void calculate() {
        kpService.acquire();
    }
}