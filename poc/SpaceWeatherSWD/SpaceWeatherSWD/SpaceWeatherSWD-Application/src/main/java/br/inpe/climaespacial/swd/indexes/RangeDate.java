package br.inpe.climaespacial.swd.indexes;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class RangeDate {
    
    private ZonedDateTime minDate;
    private ZonedDateTime maxDate;

    public ZonedDateTime getMinDate() {
        return minDate;
    }

    public void setMinDate(ZonedDateTime minDate) {
        this.minDate = minDate;
    }
    
    public ZonedDateTime getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(ZonedDateTime maxDate) {
        this.maxDate = maxDate;
    }
}
