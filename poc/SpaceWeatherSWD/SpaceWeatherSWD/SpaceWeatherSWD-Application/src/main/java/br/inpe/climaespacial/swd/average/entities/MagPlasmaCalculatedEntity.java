package br.inpe.climaespacial.swd.average.entities;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class MagPlasmaCalculatedEntity  {
	
	private MagEntity magEntity;
	private PlasmaEntity plasmaEntity;
	private CalculatedValuesEntity calculatedValuesEntity;
	
        @Inject
	public MagPlasmaCalculatedEntity(MagEntity magEntity, PlasmaEntity plasmaEntity, CalculatedValuesEntity calculatedValuesEntity) {
		this.calculatedValuesEntity = calculatedValuesEntity;
		this.plasmaEntity = plasmaEntity;
		this.magEntity = magEntity;
	}
	
	
	public MagEntity getMagEntity() {
		return magEntity;
	}
	public void setMagEntity(MagEntity magEntity) {
		this.magEntity = magEntity;
	}
	public PlasmaEntity getPlasmaEntity() {
		return plasmaEntity;
	}
	public void setPlasmaEntity(PlasmaEntity plasmaEntity) {
		this.plasmaEntity = plasmaEntity;
	}
	public CalculatedValuesEntity getCalculatedValuesEntity() {
		return calculatedValuesEntity;
	}
	public void setCalculatedValuesEntity(CalculatedValuesEntity calculatedValuesEntity) {
		this.calculatedValuesEntity = calculatedValuesEntity;
	}
	
}
