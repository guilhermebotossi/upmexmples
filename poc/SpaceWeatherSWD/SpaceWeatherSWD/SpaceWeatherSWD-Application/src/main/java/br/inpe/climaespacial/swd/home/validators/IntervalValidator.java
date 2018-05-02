
package br.inpe.climaespacial.swd.home.validators;

import java.time.ZonedDateTime;

public interface IntervalValidator {
    
    void validate(ZonedDateTime farthestFromNow, ZonedDateTime nearestFromNow, int periodSize);
    
}
