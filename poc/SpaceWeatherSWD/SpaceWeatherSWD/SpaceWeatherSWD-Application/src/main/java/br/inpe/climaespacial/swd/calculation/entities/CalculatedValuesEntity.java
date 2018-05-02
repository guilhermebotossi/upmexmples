package br.inpe.climaespacial.swd.calculation.entities;

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
@Table(name = "calculated_values", schema = "swd")
public class CalculatedValuesEntity extends BaseEntity {

    @Column(name = "time_tag", unique = true, nullable = false)
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    private ZonedDateTime timeTag;

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
