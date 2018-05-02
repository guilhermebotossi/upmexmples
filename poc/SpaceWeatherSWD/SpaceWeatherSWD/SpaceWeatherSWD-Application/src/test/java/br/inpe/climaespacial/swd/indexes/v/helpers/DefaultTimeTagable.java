package br.inpe.climaespacial.swd.indexes.v.helpers;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.commons.helpers.TimeTagable;

public class DefaultTimeTagable implements TimeTagable {

    private static final long serialVersionUID = 1L;
    
    private ZonedDateTime timeTag;

    @Override
    public void setTimeTag(ZonedDateTime timeTag) {
        this.timeTag = timeTag;
    }

    @Override
    public ZonedDateTime getTimeTag() {
        return timeTag;
    }
    
}