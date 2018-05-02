package br.inpe.climaespacial.swd.calculation.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;
import br.inpe.climaespacial.swd.calculation.entities.MagPlasmaEntity;
import br.inpe.climaespacial.swd.calculation.mappers.MagPlasmaMapper;
import br.inpe.climaespacial.swd.commons.EmbraceMockito;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultMagPlasmaReaderRepository.class)
public class MagPlasmaReaderRepositoryTest {

    @Produces
    @Mock
    private EntityManager entityManager;

    @Produces
    @Mock
    private MagPlasmaMapper magPlasmaMapper;

    @Inject
    private MagPlasmaReaderRepository magPlasmaReaderRepository;

    private static final String SELECT_SQL = "SELECT NEW br.inpe.climaespacial.swd.calculation.entities.MagPlasmaEntity(me, pe) "
            + "FROM PlasmaEntity pe, MagEntity me " + "WHERE me.timeTag = pe.timeTag AND "
            + "NOT EXISTS (SELECT cv.timeTag FROM CalculatedValuesEntity cv WHERE cv.timeTag = pe.timeTag) "
            + "ORDER BY pe.timeTag ASC";

    @Test
    public void list_called_returnsList() {
        List<MagPlasma> mpl1 = mockList(MagPlasma.class);
        List<MagPlasmaEntity> mpel = mockList(MagPlasmaEntity.class);
        TypedQuery<MagPlasmaEntity> query = EmbraceMockito.mockTypedQuery(MagPlasmaEntity.class);

        when(entityManager.createQuery(SELECT_SQL, MagPlasmaEntity.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(mpel);
        when(magPlasmaMapper.map(mpel)).thenReturn(mpl1);

        List<MagPlasma> mpl2 = magPlasmaReaderRepository.list();

        verify(entityManager, times(1)).createQuery(SELECT_SQL, MagPlasmaEntity.class);
        int minutesInDay = 1440;
        verify(query).setMaxResults(minutesInDay);
        verify(query).getResultList();
        verify(magPlasmaMapper).map(mpel);

        assertNotNull(mpl2);
        assertSame(mpl1, mpl2);
    }

}
