package br.inpe.climaespacial.swd.kp.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.inpe.climaespacial.swd.commons.converters.ZonedDateTimeAttributeConverter;
import br.inpe.climaespacial.swd.commons.entities.BaseEntity;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;

@Entity
@Dependent
@Table(name = "kp_value", schema = "swd")
public class KPValueEntity extends BaseEntity {
    
    @Column(name = "time_tag", nullable = false, unique = true)
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    private ZonedDateTime timeTag;
    
    @Column(name = "value", nullable = false)
    private Long kpValue;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "flag", nullable = false)
    private KPValueFlag kpValueFlag;
    
    @JoinColumn(name = "index_kp_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private KPEntity kpEntity;
    
    public ZonedDateTime getTimeTag() {
        return timeTag;
    }
    public void setTimeTag(ZonedDateTime timeTag) {
        this.timeTag = timeTag;
    }
    public Long getKPValue() {
        return kpValue;
    }
    public void setKPValue(Long kpValue) {
        this.kpValue = kpValue;
    }
    public KPValueFlag getKPValueFlag() {
        return kpValueFlag;
    }
    public void setKPValueFlag(KPValueFlag kpValueFlag) {
        this.kpValueFlag = kpValueFlag;
    }
    public KPEntity getKPEntity() {
        return kpEntity;
    }
    public void setKPEntity(KPEntity kpEntity) {
        this.kpEntity = kpEntity;
    }
    

}
