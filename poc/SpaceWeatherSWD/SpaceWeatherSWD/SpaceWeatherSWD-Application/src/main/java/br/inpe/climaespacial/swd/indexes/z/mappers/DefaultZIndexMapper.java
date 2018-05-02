package br.inpe.climaespacial.swd.indexes.z.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import br.inpe.climaespacial.swd.indexes.z.factories.ZIndexFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultZIndexMapper implements ZIndexMapper {

    @Inject
    private ListFactory<ZIndex> zIndexListFactory;

    @Inject
    private ZIndexFactory zIndexFactory;

    @Override
    public List<ZIndex> map(List<ZIndexEntity> zIndexEntityList) {

        throwIfNull(zIndexEntityList, "zIndexEntityList");

        List<ZIndex> zil = zIndexListFactory.create();

        zIndexEntityList.forEach(zie -> {
            ZIndex zi = zIndexFactory.create();
            zi.setTimeTag(zie.getTimeTag());
            zi.setPreValue(zie.getPreValue());
            zi.setPostValue(zie.getPostValue());
            zil.add(zi);
        });

        return zil;
    }

    @Override
    public ZIndex map(ZIndexEntity zIndexEntity) {
        throwIfNull(zIndexEntity, "zIndexEntity");
        
        ZIndex zi = zIndexFactory.create();
        zi.setTimeTag(zIndexEntity.getTimeTag());
        zi.setPreValue(zIndexEntity.getPreValue());
        zi.setPostValue(zIndexEntity.getPostValue());
        return zi;
    }

}
