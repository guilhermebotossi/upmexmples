package br.inpe.climaespacial.swd.average.dtos;

import java.time.ZonedDateTime;
import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.commons.helpers.TimeTagable;

@Dependent
public class HourlyAverage implements TimeTagable {

    private static final long serialVersionUID = 1L;
    
    private ZonedDateTime timeTag;
    private Double bxGsm;
    private Double byGsm;
    private Double bzGsm;
    private Double bt;
    private Double density;
    private Double speed;
    private Double temperature;
    private Double ey;
    private Double dpr;
    private Double rmp;

    public ZonedDateTime getTimeTag() {
        return timeTag;
    }

    public void setTimeTag(ZonedDateTime timeTag) {
        this.timeTag = timeTag;
    }

    public Double getBxGsm() {
        return bxGsm;
    }

    public void setBxGsm(Double bxGsm) {
        this.bxGsm = bxGsm;
    }

    public Double getByGsm() {
        return byGsm;
    }

    public void setByGsm(Double byGsm) {
        this.byGsm = byGsm;
    }

    public Double getBzGsm() {
        return bzGsm;
    }

    public void setBzGsm(Double bzGsm) {
        this.bzGsm = bzGsm;
    }

    public Double getBt() {
        return bt;
    }

    public void setBt(Double bt) {
        this.bt = bt;
    }

    public Double getDensity() {
        return density;
    }

    public void setDensity(Double density) {
        this.density = density;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
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
        return "HourlyAverage ["
        		+ "timeTag=" + timeTag
                + "bxGsm=" + bxGsm
                + ", byGsm=" + byGsm
                + ", bzGsm=" + bzGsm
                + ", bt=" + bt
                + ", density=" + density
                + ", speed=" + speed
                + ", temperature=" + temperature
                + ", ey=" + ey
                + ", dpr=" + dpr
                + ", rmp=" + rmp
                + "]";
    }

}
