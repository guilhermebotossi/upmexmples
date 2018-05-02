package br.inpe.climaespacial.swd.indexes.v.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import br.inpe.climaespacial.swd.indexes.v.factories.VIndexFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultVIndexMapper implements VIndexMapper {

    @Inject
    private ListFactory<VIndex> vIndexListFactory;

    @Inject
    private VIndexFactory vIndexFactory;

    @Override
    public List<VIndex> map(List<VIndexEntity> vIndexEntityList) {

        throwIfNull(vIndexEntityList, "vIndexEntityList");

        List<VIndex> vil = vIndexListFactory.create();

        vIndexEntityList.forEach(vie -> {
            VIndex vi = vIndexFactory.create();
            vi.setTimeTag(vie.getTimeTag());
            vi.setPreValue(vie.getPreValue());
            vi.setPostValue(vie.getPostValue());
            vi.setIsCycleBegin(vie.getIsCycleBegin());
            vil.add(vi);
        });

        return vil;
    }

}
