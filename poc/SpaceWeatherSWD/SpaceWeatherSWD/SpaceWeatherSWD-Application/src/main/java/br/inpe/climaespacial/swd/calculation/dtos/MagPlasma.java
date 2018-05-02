package br.inpe.climaespacial.swd.calculation.dtos;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class MagPlasma {

    private Double bzGsm;
    private Double speed;
    private Double density;
	private ZonedDateTime timeTag;



    public Double getBzGsm() {
		return bzGsm;
	}



	public void setBzGsm(Double bzGsm) {
		this.bzGsm = bzGsm;
	}



	public Double getSpeed() {
		return speed;
	}



	public void setSpeed(Double speed) {
		this.speed = speed;
	}



	public Double getDensity() {
		return density;
	}



	public void setDensity(Double density) {
		this.density = density;
	}
	
	public void setTimeTag(ZonedDateTime timeTag) {
		this.timeTag = timeTag;
	}
	
	public ZonedDateTime getTimeTag() {
		return timeTag;
	}



	@Override
    public String toString() {
        return "MagPlasma [bzGsm=" + bzGsm + ", speed=" + speed + ", density=" + density + "]";
    }


}
