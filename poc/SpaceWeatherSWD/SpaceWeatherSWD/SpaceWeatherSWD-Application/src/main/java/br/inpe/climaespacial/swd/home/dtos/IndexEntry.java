package br.inpe.climaespacial.swd.home.dtos;

import java.time.ZonedDateTime;
import javax.enterprise.context.Dependent;

@Dependent
public class IndexEntry {

    private ZonedDateTime timeTag;
    private Double preValue;
    private Double postValue;

    public ZonedDateTime getTimeTag() {
        return timeTag;
    }

    public void setTimeTag(ZonedDateTime timeTag) {
        this.timeTag = timeTag;
    }

    public Double getPreValue() {
        return preValue;
    }

    public void setPreValue(Double postValue) {
        this.preValue = postValue;
    }

    public Double getPostValue() {
        return postValue;
    }

    public void setPostValue(Double postValue) {
        this.postValue = postValue;
    }

}
