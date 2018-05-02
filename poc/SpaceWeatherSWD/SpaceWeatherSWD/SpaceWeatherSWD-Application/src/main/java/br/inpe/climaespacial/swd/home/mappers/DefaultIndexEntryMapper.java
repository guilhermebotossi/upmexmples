package br.inpe.climaespacial.swd.home.mappers;

import br.inpe.climaespacial.swd.home.factories.IndexEntryFactory;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.home.dtos.IndexEntry;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultIndexEntryMapper implements IndexEntryMapper {
    
    @Inject
    private ListFactory<IndexEntry> indexEntryListFactory;
    
    @Inject
    private IndexEntryFactory indexEntryFactory;

    @Override
    public List<IndexEntry> mapB(List<BIndex> bIndexList) {
        throwIfNull(bIndexList, "bIndexList");
        List<IndexEntry> iel = indexEntryListFactory.create();
        bIndexList.forEach(bi -> {
            IndexEntry ie = indexEntryFactory.create();
            ie.setTimeTag(bi.getTimeTag());
            ie.setPreValue(bi.getPreValue());
            ie.setPostValue(bi.getPostValue());
            iel.add(ie);
        });
        return iel;
    }

    @Override
    public List<IndexEntry> mapC(List<CIndex> cIndexList) {
        throwIfNull(cIndexList, "cIndexList");
        List<IndexEntry> iel = indexEntryListFactory.create();
        cIndexList.forEach(ci -> {
            IndexEntry ie = indexEntryFactory.create();
            ie.setTimeTag(ci.getTimeTag());
            ie.setPreValue(ci.getPreValue());
            ie.setPostValue(ci.getPostValue());
            iel.add(ie);
        });
        return iel;
    }

    @Override
    public List<IndexEntry> mapV(List<VIndex> vIndexList) {
        throwIfNull(vIndexList, "vIndexList");
        List<IndexEntry> iel = indexEntryListFactory.create();
        vIndexList.forEach(vi -> {
            IndexEntry ie = indexEntryFactory.create();
            ie.setTimeTag(vi.getTimeTag());
            ie.setPreValue(vi.getPreValue());
            ie.setPostValue(vi.getPostValue());
            iel.add(ie);
        });
        return iel;
    }

    @Override
    public List<IndexEntry> mapZ(List<ZIndex> zIndexList) {
        throwIfNull(zIndexList, "zIndexList");
        List<IndexEntry> iel = indexEntryListFactory.create();
        zIndexList.forEach(zi -> {
            IndexEntry ie = indexEntryFactory.create();
            ie.setTimeTag(zi.getTimeTag());
            ie.setPreValue(zi.getPreValue());
            ie.setPostValue(zi.getPostValue());
            iel.add(ie);
        });
        return iel;
    }

}
