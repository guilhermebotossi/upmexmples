package br.inpe.climaespacial.swd.home.validators;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;
import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.createCustom;

import java.time.Duration;
import java.time.ZonedDateTime;
import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.commons.exceptions.ArgumentException;

@Dependent
public class DefaultIntervalValidator implements IntervalValidator {

    @Override
    public void validate(ZonedDateTime farthestFromNow, ZonedDateTime nearestFromNow, int periodSize) {

        throwIfNull(farthestFromNow, "farthestFromNow");

        throwIfNull(nearestFromNow, "nearestFromNow");

        if (farthestFromNow.isAfter(nearestFromNow)) {
            throw createCustom("Argument \"farthestFromNow\" cannot be after than \"nearestFromNow\".", ArgumentException.class);
        }

        Duration d = Duration.between(farthestFromNow, nearestFromNow);

        if (d.toDays() > periodSize) {
            throw createCustom("Period size exceeded.", ArgumentException.class);
        }
    }

}
