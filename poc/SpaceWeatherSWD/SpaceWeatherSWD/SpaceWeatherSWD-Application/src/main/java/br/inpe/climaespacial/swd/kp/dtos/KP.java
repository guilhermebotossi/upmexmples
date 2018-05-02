package br.inpe.climaespacial.swd.kp.dtos;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;

@Dependent
public class KP {
    private ZonedDateTime timeTag;
    private List<KPValue> kpValueList;
    private Long sum;
    private KPValueFlag sumFlag;
    private Long ap;
    private Double cp;
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
    public String getMostDisturbedAndQuietDays() {
        return mostDisturbedAndQuietDays;
    }
    public void setMostDisturbedAndQuietDays(String mostDisturbedAndQuietDays) {
        this.mostDisturbedAndQuietDays = mostDisturbedAndQuietDays;
    }
    public List<KPValue> getKPValueList() {
        return kpValueList;
    }
    public void setKPValueList(List<KPValue> kpValueList) {
        this.kpValueList = kpValueList;
    }
}
