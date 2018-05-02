package br.inpe.climaespacial.swd.acquisition.entities;

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
@Table(name = "mag", schema = "swd")
public class MagEntity extends BaseEntity {

    @Column(name = "time_tag", nullable = true, unique = true)
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    private ZonedDateTime timeTag;

    @Column(name = "bx_gsm", nullable = true)
    private Double bxGsm;

    @Column(name = "by_gsm", nullable = true)
    private Double byGsm;

    @Column(name = "bz_gsm", nullable = true)
    private Double bzGsm;

    @Column(name = "lat_gsm", nullable = true)
    private Double latGsm;

    @Column(name = "lon_gsm", nullable = true)
    private Double lonGsm;

    @Column(name = "bt", nullable = true)
    private Double bt;

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

    public Double getLatGsm() {
        return latGsm;
    }

    public void setLatGsm(Double latGsm) {
        this.latGsm = latGsm;
    }

    public Double getLonGsm() {
        return lonGsm;
    }

    public void setLonGsm(Double lonGsm) {
        this.lonGsm = lonGsm;
    }

    public Double getBt() {
        return bt;
    }

    public void setBt(Double bt) {
        this.bt = bt;
    }
}
