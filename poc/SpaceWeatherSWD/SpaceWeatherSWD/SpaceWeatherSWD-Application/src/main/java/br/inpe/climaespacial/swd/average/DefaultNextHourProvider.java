package br.inpe.climaespacial.swd.average;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.average.providers.HourlyAverageNextHourRepository;
import br.inpe.climaespacial.swd.average.providers.NextHourProvider;
import br.inpe.climaespacial.swd.average.repositories.MagPlasmaCalculatedValuesNextHourRepository;
import br.inpe.climaespacial.swd.average.validators.NextHourValidator;
import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;

@Dependent
public class DefaultNextHourProvider implements NextHourProvider {

    @Inject
    private HourlyAverageNextHourRepository hourlyAverageNextHourRepository;

    @Inject
    private MagPlasmaCalculatedValuesNextHourRepository magPlasmaNextHourRepository;

    @Inject
    private NextHourValidator nextHourValidator;
    
    @Inject
    private DateTimeHelper dateTimeHelper;

    @Override
    public ZonedDateTime getNextHour() {
        ZonedDateTime nextHour = hourlyAverageNextHourRepository.getNextHour();

        if (nextHour == null) {
            nextHour = magPlasmaNextHourRepository.getNextHour();
        }

        if (nextHour != null && nextHourValidator.validate(nextHour)) {
            return dateTimeHelper.truncateToMinute(nextHour);
        }

        return null;
    }
}
