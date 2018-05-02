package br.inpe.climaespacial.swd.average.mappers;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.average.entities.MagPlasmaCalculatedEntity;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.calculation.factories.MagPlasmaCalculatedFactory;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultMagPlasmaCalculatedMapper implements MagPlasmaCalculatedMapper {

    @Inject
    private MagPlasmaCalculatedFactory magPlasmaCalculatedFactory;

    @Inject
    private ListFactory<MagPlasmaCalculated> magPlasmaCalculatedListFactory;

    @Override
    public List<MagPlasmaCalculated> map(List<MagPlasmaCalculatedEntity> magPlasmaCalculatedEntityList) {

        throwIfNull(magPlasmaCalculatedEntityList, "magPlasmaCalculatedEntityList");

        List<MagPlasmaCalculated> mpcl = magPlasmaCalculatedListFactory.create();

        magPlasmaCalculatedEntityList.forEach((mpce) -> {
            MagPlasmaCalculated mpc = magPlasmaCalculatedFactory.create();
            MagEntity me = mpce.getMagEntity();
            PlasmaEntity pe = mpce.getPlasmaEntity();
            CalculatedValuesEntity cve = mpce.getCalculatedValuesEntity();

            mpc.setBt(me.getBt());
            mpc.setBxGsm(me.getBxGsm());
            mpc.setByGsm(me.getByGsm());
            mpc.setBzGsm(me.getBzGsm());
            mpc.setDensity(pe.getDensity());
            mpc.setSpeed(pe.getSpeed());
            mpc.setTemperature(pe.getTemperature());
            mpc.setDpr(cve.getDpr());
            mpc.setEy(cve.getEy());
            mpc.setRmp(cve.getRmp());

            mpcl.add(mpc);
        });

        return mpcl;
    }

}
