package br.inpe.climaespacial.swd.acquisition;

import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.acquisition.services.DataAquisition;

@Singleton
@Startup
@DependsOn("DefaultTimeZone")
public class AcquisitionScheduler {

    @Inject
    private DataAquisition dataAquisition;

    @AccessTimeout(value = 2, unit = TimeUnit.MINUTES)
    @Schedule(hour = "*", minute = "*", second = "0", persistent = false)
    public void getData() {
        dataAquisition.acquire();
    }

}
