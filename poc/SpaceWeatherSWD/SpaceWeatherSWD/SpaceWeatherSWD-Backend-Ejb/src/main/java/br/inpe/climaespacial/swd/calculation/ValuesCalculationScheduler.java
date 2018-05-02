package br.inpe.climaespacial.swd.calculation;

import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.TransactionTimeout;

import br.inpe.climaespacial.swd.calculation.services.ValuesCalculation;

@Singleton
@Startup
@DependsOn("DefaultTimeZone")
public class ValuesCalculationScheduler {

    @Inject
    private ValuesCalculation valuesCalculation;

    @AccessTimeout(value = 2, unit = TimeUnit.MINUTES)
    @TransactionTimeout(value = 40, unit = TimeUnit.MINUTES)
    @Schedule(hour = "*", minute = "*", second = "0", persistent = false)
    public void calculate() {
        valuesCalculation.calculate();
    }

}
