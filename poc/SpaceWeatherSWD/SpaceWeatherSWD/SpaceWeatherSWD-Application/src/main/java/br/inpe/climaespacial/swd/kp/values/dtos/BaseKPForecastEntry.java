package br.inpe.climaespacial.swd.kp.values.dtos;

import java.time.ZonedDateTime;
import javax.enterprise.context.Dependent;

@Dependent
public abstract class BaseKPForecastEntry {

    private ZonedDateTime timeTag;
    private boolean forecast;
    private ZonedDateTime presentationTimeTag;

    public ZonedDateTime getTimeTag() {
        return timeTag;
    }

    public void setTimeTag(ZonedDateTime timeTag) {
        this.timeTag = timeTag;
    }

    public boolean isForecast() {
        return forecast;
    }

    public void setForecast(boolean forecast) {
        this.forecast = forecast;
    }

	public ZonedDateTime getPresentationTimeTag() {
		return presentationTimeTag;
	}

	public void setPresentationTimeTag(ZonedDateTime presentationTimeTag) {
		this.presentationTimeTag = presentationTimeTag;
	}
    
}
