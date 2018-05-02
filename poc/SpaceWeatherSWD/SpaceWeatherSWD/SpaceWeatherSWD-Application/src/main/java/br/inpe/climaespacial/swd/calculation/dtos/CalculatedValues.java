package br.inpe.climaespacial.swd.calculation.dtos;

import java.time.ZonedDateTime;

public class CalculatedValues {

    private ZonedDateTime timeTag;
    private Double ey;
    private Double dpr;
    private Double rmp;

    public ZonedDateTime getTimeTag() {
        return timeTag;
    }

    public void setTimeTag(ZonedDateTime timeTag) {
        this.timeTag = timeTag;
    }

    public Double getEy() {
        return ey;
    }

    public void setEy(Double ey) {
        this.ey = ey;
    }

    public Double getDpr() {
        return dpr;
    }

    public void setDpr(Double dpr) {
        this.dpr = dpr;
    }

    public Double getRmp() {
        return rmp;
    }

    public void setRmp(Double rmp) {
        this.rmp = rmp;
    }

    @Override
    public String toString() {
        return "CalculatedValues [timeTag=" + timeTag + ", ey=" + ey + ", dpr=" + dpr + ", rmp=" + rmp + "]";
    }

}
