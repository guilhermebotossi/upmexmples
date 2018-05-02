package br.inpe.climaespacial.swd.values.ey.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.ey.value.dtos.EY;
import br.inpe.climaespacial.swd.values.ey.value.entities.EYEntity;
import br.inpe.climaespacial.swd.values.ey.value.factories.EYFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultEYMapper implements EYMapper {

    @Inject
    private ListFactory<EY> eyListFactory;

    @Inject
    private EYFactory eyFactory;

    @Override
    public List<EY> map(List<EYEntity> eyEntityList) {
        throwIfNull(eyEntityList, "eyEntityList");

        List<EY> eyl = eyListFactory.create();

        eyEntityList.forEach(eye -> eyl.add(convertToEY(eye)));
        return eyl;
    }

    private EY convertToEY(EYEntity eye) {
        return eyFactory.create(eye.getTimeTag(), eye.getEY());
    }

}
