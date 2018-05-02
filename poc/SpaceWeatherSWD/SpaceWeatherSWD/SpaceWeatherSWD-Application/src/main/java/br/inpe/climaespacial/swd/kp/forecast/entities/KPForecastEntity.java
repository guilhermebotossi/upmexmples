package br.inpe.climaespacial.swd.kp.forecast.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.inpe.climaespacial.swd.commons.converters.ZonedDateTimeAttributeConverter;
import br.inpe.climaespacial.swd.commons.entities.BaseEntity;

@Dependent
@Entity
@Table(name = "kp_forecast", schema = "swd", uniqueConstraints = @UniqueConstraint(columnNames = { "predicted_time_tag", "indexes_time_tag" }))
public class KPForecastEntity extends BaseEntity {
    
    @Column(name = "predicted_time_tag", nullable = false)
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    private ZonedDateTime predictedTimeTag;
    
    @Column(name = "indexes_time_tag", nullable = false)
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    private ZonedDateTime indexesTimeTag;
    
    @Column(name = "probability_1", nullable = true)
    private Double probability1;
    
    @Column(name = "probability_2", nullable = true)
    private Double probability2;
    
    @Column(name = "probability_3", nullable = true)
    private Double probability3;
    
    @Column(name = "upperLimit_1", nullable = true)
    private Double upperLimit1;
    
    @Column(name = "upperLimit_2", nullable = true)
    private Double upperLimit2;
    
    @Column(name = "upperLimit_3", nullable = true)
    private Double upperLimit3;
    
    @Column(name = "inferiorLimit_1", nullable = true)
    private Double inferiorLimit1;
    
    @Column(name = "inferiorLimit_2", nullable = true)
    private Double inferiorLimit2;
    
    @Column(name = "inferiorLimit_3", nullable = true)
    private Double inferiorLimit3;

    public ZonedDateTime getPredictedTimeTag() {
        return predictedTimeTag;
    }

    public void setPredictedTimeTag(ZonedDateTime timeTag) {
        this.predictedTimeTag = timeTag;
    }

    public void setIndexesTimeTag(ZonedDateTime indexesTimeTag) {
        this.indexesTimeTag = indexesTimeTag;
        
    }

    public ZonedDateTime getIndexesTimeTag() {
        return indexesTimeTag;
    }

    public Double getProbability1() {
        return probability1;
    }

    public void setProbability1(Double probability1) {
        this.probability1 = probability1;
    }

    public Double getProbability2() {
        return probability2;
    }

    public void setProbability2(Double probability2) {
        this.probability2 = probability2;
    }

    public Double getProbability3() {
        return probability3;
    }

    public void setProbability3(Double probability3) {
        this.probability3 = probability3;
    }

    public Double getUpperLimit1() {
        return upperLimit1;
    }

    public void setUpperLimit1(Double upperLimit1) {
        this.upperLimit1 = upperLimit1;
    }

    public Double getUpperLimit2() {
        return upperLimit2;
    }

    public void setUpperLimit2(Double upperLimit2) {
        this.upperLimit2 = upperLimit2;
    }

    public Double getUpperLimit3() {
        return upperLimit3;
    }

    public void setUpperLimit3(Double upperLimit3) {
        this.upperLimit3 = upperLimit3;
    }

    public Double getInferiorLimit1() {
        return inferiorLimit1;
    }

    public void setInferiorLimit1(Double inferiorLimit1) {
        this.inferiorLimit1 = inferiorLimit1;
    }

    public Double getInferiorLimit2() {
        return inferiorLimit2;
    }

    public void setInferiorLimit2(Double inferiorLimit2) {
        this.inferiorLimit2 = inferiorLimit2;
    }

    public Double getInferiorLimit3() {
        return inferiorLimit3;
    }

    public void setInferiorLimit3(Double inferiorLimit3) {
        this.inferiorLimit3 = inferiorLimit3;
    }
    
}
