package br.inpe.climaespacial.swd.acquisition.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.Arrays;
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

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.acquisition.mappers.PlasmaEntityMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultPlasmaWriterRepository.class)
public class PlasmaWriterRepositoryTest {

    private static final String SELECT = "SELECT CASE WHEN(COUNT(*) > 0) THEN true ELSE false END FROM PlasmaEntity pe WHERE pe.timeTag = :timeTag";

    @Produces
    @Mock
    private PlasmaEntityMapper plasmaEntityMapper;

    @Produces
    @Mock
    private EntityManager entityManager;

    @Inject
    private PlasmaWriterRepository plasmaWriterRepository;

    @Test
    public void save_called_WithPlasmaListNull_throws() {
        List<Plasma> pl = null;
        RuntimeException re = null;

        try {
            plasmaWriterRepository.save(pl);
        } catch (RuntimeException ex) {
            re = ex;
        }

        assertNotNull(re);
        assertEquals("Argument \"plasmaList\" cannot be null.", re.getMessage());
    }

    @Test
    public void save_calledWithExistingMag_isIdempotent() {
        TypedQuery<Boolean> tq = mockTypedQuery(Boolean.class);
        when(entityManager.createQuery(SELECT, Boolean.class)).thenReturn(tq);
        when(tq.getSingleResult()).thenReturn(true);
        Plasma p1 = mock(Plasma.class);
        List<Plasma> pl = Arrays.asList(p1);
        PlasmaEntity pe = mock(PlasmaEntity.class);
        ZonedDateTime tt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(pe.getTimeTag()).thenReturn(tt);
        List<PlasmaEntity> pel = Arrays.asList(pe);
        when(plasmaEntityMapper.map(pl)).thenReturn(pel);

        plasmaWriterRepository.save(pl);

        verify(plasmaEntityMapper, times(1)).map(refEq(pl));
        verify(entityManager, times(1)).createQuery(SELECT, Boolean.class);
        verify(tq, times(1)).setParameter("timeTag", tt);
        verify(tq, times(1)).getSingleResult();
    }

    @Test
    public void save_called_savesList() {
        TypedQuery<Boolean> tq = mockTypedQuery(Boolean.class);
        when(entityManager.createQuery(SELECT, Boolean.class)).thenReturn(tq);
        when(tq.getSingleResult()).thenReturn(false);
        List<Plasma> pl = mockList(Plasma.class);
        PlasmaEntity pe1 = mock(PlasmaEntity.class);
        ZonedDateTime tt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(pe1.getTimeTag()).thenReturn(tt1);
        PlasmaEntity pe2 = mock(PlasmaEntity.class);
        ZonedDateTime tt2 = ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]");
        when(pe2.getTimeTag()).thenReturn(tt2);
        List<PlasmaEntity> pel = Arrays.asList(pe1, pe2);
        when(plasmaEntityMapper.map(pl)).thenReturn(pel);

        plasmaWriterRepository.save(pl);

        assertNotNull(pl);
        verify(plasmaEntityMapper, times(1)).map(refEq(pl));
        verify(entityManager, times(2)).createQuery(SELECT, Boolean.class);
        verify(tq, times(1)).setParameter(eq("timeTag"), refEq(tt1));
        verify(tq, times(1)).setParameter(eq("timeTag"), refEq(tt2));
        verify(tq, times(2)).getSingleResult();
        verify(entityManager, times(1)).persist(refEq(pe1));
        verify(entityManager, times(1)).persist(refEq(pe2));
    }

}
