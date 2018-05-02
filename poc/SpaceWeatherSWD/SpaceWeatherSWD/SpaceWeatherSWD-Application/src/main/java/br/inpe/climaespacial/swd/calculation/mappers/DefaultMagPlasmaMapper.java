package br.inpe.climaespacial.swd.calculation.mappers;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;
import br.inpe.climaespacial.swd.calculation.entities.MagPlasmaEntity;
import br.inpe.climaespacial.swd.calculation.factories.MagPlasmaFactory;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultMagPlasmaMapper implements MagPlasmaMapper {

    @Inject
    private MagPlasmaFactory magPlasmaFactory;

    @Inject
    private ListFactory<MagPlasma> magPlasmaListFactory;

    @Override
    public List<MagPlasma> map(List<MagPlasmaEntity> magPlasmaEntityList) {

        throwIfNull(magPlasmaEntityList, "magPlasmaEntityList");

        List<MagPlasma> mpl = magPlasmaListFactory.create();

        magPlasmaEntityList.forEach((MagPlasmaEntity mpe) -> {
            mpl.add(magPlasmaEntityToMagPlasma(mpe));
        });

        return mpl;
    }

    private MagPlasma magPlasmaEntityToMagPlasma(MagPlasmaEntity mpe) {
        MagPlasma mp = magPlasmaFactory.create();

        MagEntity magEntity = mpe.getMagEntity();
        mp.setBzGsm(magEntity.getBzGsm());
        mp.setTimeTag(magEntity.getTimeTag());

        PlasmaEntity plasmaEntity = mpe.getPlasmaEntity();
        mp.setSpeed(plasmaEntity.getSpeed());
        mp.setDensity(plasmaEntity.getDensity());

        return mp;
    }

}
