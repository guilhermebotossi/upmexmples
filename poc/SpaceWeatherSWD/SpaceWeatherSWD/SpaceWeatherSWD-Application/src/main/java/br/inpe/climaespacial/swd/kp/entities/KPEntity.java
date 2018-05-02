package br.inpe.climaespacial.swd.kp.entities;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.inpe.climaespacial.swd.commons.converters.ZonedDateTimeAttributeConverter;
import br.inpe.climaespacial.swd.commons.entities.BaseEntity;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;

@Dependent
@Entity
@Table(name = "index_kp", schema = "swd")
public class KPEntity extends BaseEntity {
    
    @Column(name = "time_tag", nullable = false, unique = true)
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    private ZonedDateTime timeTag;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kpEntity")
    private List<KPValueEntity> kpValueList;
    
    @Column(name = "kp_sum", nullable = true)
    private Long sum;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "kp_sum_flag", nullable = true)
    private KPValueFlag sumFlag;
    
    @Column(name = "ap", nullable = true)
    private Long ap;
    
    @Column(name = "cp", nullable = true)
    private Double cp;
    
    @Column(name = "most_disturbed_and_quiet_days", nullable = true)
    private String mostDisturbedAndQuietDays;
    
    
    public ZonedDateTime getTimeTag() {
        return timeTag;
    }
    public void setTimeTag(ZonedDateTime timeTag) {
        this.timeTag = timeTag;
    }
    public Long getSum() {
        return sum;
    }
    public void setSum(Long sum) {
        this.sum = sum;
    }
    public KPValueFlag getSumFlag() {
        return sumFlag;
    }
    public void setSumFlag(KPValueFlag sumFlag) {
        this.sumFlag = sumFlag;
    }
    public Long getAp() {
        return ap;
    }
    public void setAp(Long ap) {
        this.ap = ap;
    }
    public Double getCp() {
        return cp;
    }
    public void setCp(Double cp) {
        this.cp = cp;
    }
    public void setMostDisturbedAndQuietDays(String mostDisturbedAndQuietDays) {
        this.mostDisturbedAndQuietDays = mostDisturbedAndQuietDays;
    }
    public String getMostDisturbedAndQuietDays() {
        return mostDisturbedAndQuietDays;
    }
    public List<KPValueEntity> getKPValueList() {
        return kpValueList;
    }
    public void setKPValueList(List<KPValueEntity> kpValueList) {
        this.kpValueList = kpValueList;
    }
    
}
