package br.inpe.climaespacial.swd.values.bt.value.mappers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.bt.value.dtos.BT;
import br.inpe.climaespacial.swd.values.bt.value.entities.BTEntity;
import br.inpe.climaespacial.swd.values.bt.value.factories.BTFactory;

@Dependent
public class DefaultBTMapper implements BTMapper {

    @Inject
    private ListFactory<BT> btListFactory;

    @Inject
    private BTFactory btFactory;

    @Override
    public List<BT> map(List<BTEntity> btEntityList) {

        throwIfNull(btEntityList, "btEntityList");

        List<BT> btl = btListFactory.create();

        btEntityList.forEach(bte -> btl.add(convertToBT(bte)));
        return btl;
    }

    private BT convertToBT(BTEntity bte) {
        return btFactory.create(bte.getTimeTag(), bte.getBT());
    }

}
