package br.inpe.climaespacial.swd.average.entities;

import br.inpe.climaespacial.swd.commons.converters.ZonedDateTimeAttributeConverter;
import br.inpe.climaespacial.swd.commons.entities.BaseEntity;
import java.time.ZonedDateTime;
import javax.enterprise.context.Dependent;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Dependent
@Entity
@Table(name = "hourly_average", schema = "swd")
public class HourlyAverageEntity extends BaseEntity {

    @Column(name = "time_tag", unique = true, nullable = false)
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    private ZonedDateTime timeTag;

    @Column(name = "bx_gsm", nullable = true)
    private Double bxGsm;

    @Column(name = "by_gsm", nullable = true)
    private Double byGsm;

    @Column(name = "bz_gsm", nullable = true)
    private Double bzGsm;

    @Column(name = "bt", nullable = true)
    private Double bt;

    @Column(name = "density", nullable = true)
    private Double density;

    @Column(name = "speed", nullable = true)
    private Double speed;

    @Column(name = "temperature", nullable = true)
    private Double temperature;

    @Column(name = "ey", nullable = true)
    private Double ey;

    @Column(name = "dpr", nullable = true)
    private Double dpr;

    @Column(name = "rmp", nullable = true)
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
}
