package br.inpe.climaespacial.swd.calculation.entities;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;

@Dependent
public class MagPlasmaEntity {

    private MagEntity magEntity;

    private PlasmaEntity plasmaEntity;

    public MagPlasmaEntity(MagEntity magEntity, PlasmaEntity plasmaEntity) {
        this.magEntity = magEntity;
        this.plasmaEntity = plasmaEntity;
    }

    public MagEntity getMagEntity() {
        return magEntity;
    }

    public PlasmaEntity getPlasmaEntity() {
        return plasmaEntity;
    }
}
