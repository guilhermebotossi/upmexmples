package br.inpe.climaespacial.swd.commons;

import java.util.TimeZone;

import javax.enterprise.context.Dependent;

@Dependent
public class DefaultTimeZoneConfigurator implements TimeZoneConfigurator {

    @Override
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
