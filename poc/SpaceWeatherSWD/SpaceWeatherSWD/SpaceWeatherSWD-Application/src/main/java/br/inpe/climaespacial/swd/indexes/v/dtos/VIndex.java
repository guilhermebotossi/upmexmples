package br.inpe.climaespacial.swd.indexes.v.dtos;

import java.time.ZonedDateTime;
import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.commons.helpers.TimeTagable;

@Dependent
public class VIndex implements TimeTagable {

    private static final long serialVersionUID = 1L;
    private ZonedDateTime timeTag;
    private Double preValue;
    private Double postValue;
    private boolean isCycleBegin;

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

    public boolean getIsCycleBegin() {
        return isCycleBegin;
    }

    public void setIsCycleBegin(boolean isCycleBegin) {
        this.isCycleBegin = isCycleBegin;
    }

}
