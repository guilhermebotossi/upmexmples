package br.inpe.climaespacial.swd.home.services;

import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.home.dtos.IndexData;
import br.inpe.climaespacial.swd.home.dtos.IndexEntry;
import br.inpe.climaespacial.swd.home.factories.IndexDataFactory;
import br.inpe.climaespacial.swd.home.mappers.IndexEntryMapper;
import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.indexes.IndexesReaderRepository;
import br.inpe.climaespacial.swd.indexes.RangeDate;
import br.inpe.climaespacial.swd.indexes.RangeDateFactory;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.repositories.BIndexReaderRepository;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.repositories.CIndexReaderRepository;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.repositories.VIndexReaderRepository;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.repositories.ZIndexReaderRepository;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Dependent
public class DefaultIndexesService implements IndexesService {

    private static final int DAYS_TO_SUBTRACT = 6;
    private static final int PERIOD_SIZE = 31;

    @Inject
    private IntervalValidator intervalValidator;

    @Inject
    private IndexesReaderRepository indexesReaderRepository;

    @Inject
    private BIndexReaderRepository bIndexReaderRepository;

    @Inject
    private CIndexReaderRepository cIndexReaderRepository;

    @Inject
    private VIndexReaderRepository vIndexReaderRepository;

    @Inject
    private ZIndexReaderRepository zIndexReaderRepository;

    @Inject
    private IndexEntryMapper indexEntryMapper;

    @Inject
    private IndexDataFactory indexDataFactory;

    @Inject
    private RangeDateFactory rangeDateFactory;

    @Inject
    private DateTimeHelper dateTimeHelper;

    @Override
    public RangeDate getRangeDate() {
        ZonedDateTime lid = indexesReaderRepository.lastIndexesDate();

        ZonedDateTime tlid = dateTimeHelper.truncate(lid);
       
        ZonedDateTime fid = tlid== null ? null : tlid.minusDays(DAYS_TO_SUBTRACT);
        return rangeDateFactory.create(fid, tlid);
    }

    @Override
    @Transactional
    public IndexData getIndexData(ZonedDateTime farthestFromNow, ZonedDateTime nearestFromNow) {

        intervalValidator.validate(farthestFromNow, nearestFromNow, PERIOD_SIZE);

        nearestFromNow = dateTimeHelper.setMaxDayTime(nearestFromNow);

        List<BIndex> bil = bIndexReaderRepository.listByPeriod(farthestFromNow, nearestFromNow);
        List<IndexEntry> biel = indexEntryMapper.mapB(bil);

        List<CIndex> cil = cIndexReaderRepository.listByPeriod(farthestFromNow, nearestFromNow);
        List<IndexEntry> ciel = indexEntryMapper.mapC(cil);

        List<VIndex> vil = vIndexReaderRepository.listByPeriod(farthestFromNow, nearestFromNow);
        List<IndexEntry> viel = indexEntryMapper.mapV(vil);

        List<ZIndex> zil = zIndexReaderRepository.listByPeriod(farthestFromNow, nearestFromNow);
        List<IndexEntry> ziel = indexEntryMapper.mapZ(zil);

        return indexDataFactory.create(biel, ciel, viel, ziel);
    }

}
