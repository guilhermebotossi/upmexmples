package br.inpe.climaespacial.swd;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.TimeZoneConfigurator;

@Singleton
@Startup
public class DefaultTimeZone {
    
    @Inject
    private TimeZoneConfigurator timeZoneConfigurator;
    
    @PostConstruct
    public void init() {
        timeZoneConfigurator.init();
    }

}
