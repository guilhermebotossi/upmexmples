package br.inpe.climaespacial.swd.indexes.v.entities;

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
@Table(name = "index_v", schema = "swd")
public class VIndexEntity extends BaseEntity {

    @Column(name = "time_tag", unique = true, nullable = false)
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    private ZonedDateTime timeTag;

    @Column(name = "pre_index_value", nullable = true)
    private Double preValue;

    @Column(name = "post_index_value", nullable = true)
    private Double postValue;
    
    @Column(name = "is_cycle_begin", nullable = false)
    private Boolean isCycleBegin;

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

    public Boolean getIsCycleBegin() {
        return isCycleBegin;
    }

    public void setIsCycleBegin(Boolean isCycleBegin) {
        this.isCycleBegin = isCycleBegin;
    }

}
