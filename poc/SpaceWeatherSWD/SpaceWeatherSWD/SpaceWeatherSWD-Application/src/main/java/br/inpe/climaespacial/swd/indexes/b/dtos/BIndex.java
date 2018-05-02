package br.inpe.climaespacial.swd.indexes.b.dtos;

import java.time.ZonedDateTime;
import javax.enterprise.context.Dependent;

@Dependent
public class BIndex {

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

    public void setPreValue(Double preValue) {
        this.preValue = preValue;
    }

    public Double getPostValue() {
        return postValue;
    }

    public void setPostValue(Double postValue) {
        this.postValue = postValue;
    }

}
