package br.inpe.climaespacial.swd.kp.acquisition.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.inpe.climaespacial.swd.commons.converters.ZonedDateTimeAttributeConverter;
import br.inpe.climaespacial.swd.commons.entities.BaseEntity;

@Entity
@Table(name = "kp_download_history", schema = "swd")
@Dependent
public class KPDownloadHistoryEntity extends BaseEntity {
    
    @Column(name = "period", unique = true, nullable = false)
    @Convert(converter = ZonedDateTimeAttributeConverter.class)
    private ZonedDateTime period;
    
    @Column(name = "is_complete", nullable = false)
    private Boolean complete;
    

    public Boolean isComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public ZonedDateTime getPeriod() {
        return period;
    }

    public void setPeriod(ZonedDateTime period) {
        this.period = period;
    }
    
    
    

}
