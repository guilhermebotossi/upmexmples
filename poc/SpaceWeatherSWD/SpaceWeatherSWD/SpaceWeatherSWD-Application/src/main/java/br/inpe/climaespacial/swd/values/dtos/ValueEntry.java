package br.inpe.climaespacial.swd.values.dtos;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.commons.helpers.TimeTagable;


@Dependent
public class ValueEntry implements TimeTagable {
    
    private static final long serialVersionUID = 1L;
    
    
    private ZonedDateTime timeTag;
    private Double value;
    
    
    public ZonedDateTime getTimeTag() {
        return timeTag;
    }
    public void setTimeTag(ZonedDateTime timeTag) {
        this.timeTag = timeTag;
    }
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
}

