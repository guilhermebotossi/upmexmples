package br.inpe.climaespacial.swd.kp.acquisition.dtos;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class KPDownloadHistory {
    
    private ZonedDateTime period;
    private Boolean complete;

    public Boolean isComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public ZonedDateTime getPeriod() {
        return period;
    }

    public void setPeriod(ZonedDateTime period) {
        this.period = period;
    }
    
    
    

}
