package br.inpe.climaespacial.swd.kp.dtos;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;

@Dependent
public class KPValue {
    private ZonedDateTime timeTag;
    private Long kpValue;
    private KPValueFlag kpValueFlag;
    
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
    
    
}
