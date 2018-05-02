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
@Table(name = "plasma", schema = "swd")
public class PlasmaEntity extends BaseEntity {

    @Column(name = "time_tag", nullable = false, unique = true)
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    private ZonedDateTime timeTag;

    @Column(name = "density", nullable = true)
    private Double density;

    @Column(name = "speed", nullable = true)
    private Double speed;

    @Column(name = "temperature", nullable = true)
    private Double temperature;

    public ZonedDateTime getTimeTag() {
        return timeTag;
    }

    public void setTimeTag(ZonedDateTime timeTag) {
        this.timeTag = timeTag;
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
}
