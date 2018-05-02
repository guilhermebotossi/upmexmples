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

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.mappers.MagEntityMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultMagWriterRepository.class)
public class MagWriterRepositoryTest {

    private static final String SELECT = "SELECT CASE WHEN(COUNT(*) > 0) THEN true ELSE false END FROM MagEntity me WHERE me.timeTag = :timeTag";

    @Produces
    @Mock
    private MagEntityMapper magEntityMapper;

    @Produces
    @Mock
    private EntityManager entityManager;

    @Inject
    private MagWriterRepository magWriterRepository;

    @Test
    public void save_called_WithMagListNull_throws() {
        List<Mag> ml = null;
        RuntimeException re = null;

        try {
            magWriterRepository.save(ml);
        } catch (RuntimeException ex) {
            re = ex;
        }

        assertNotNull(re);
        assertEquals("Argument \"magList\" cannot be null.", re.getMessage());
    }

    @Test
    public void save_calledWithExistingMag_isIdempotent() {
        TypedQuery<Boolean> tq = mockTypedQuery(Boolean.class);
        when(entityManager.createQuery(SELECT, Boolean.class)).thenReturn(tq);
        when(tq.getSingleResult()).thenReturn(true);
        Mag m1 = mock(Mag.class);
        List<Mag> ml = Arrays.asList(m1);
        MagEntity me = mock(MagEntity.class);
        ZonedDateTime tt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(me.getTimeTag()).thenReturn(tt);
        List<MagEntity> mel = Arrays.asList(me);
        when(magEntityMapper.map(ml)).thenReturn(mel);

        magWriterRepository.save(ml);

        verify(magEntityMapper, times(1)).map(refEq(ml));
        verify(entityManager, times(1)).createQuery(SELECT, Boolean.class);
        verify(tq, times(1)).setParameter("timeTag", tt);
        verify(tq, times(1)).getSingleResult();
    }

    @Test
    public void save_called_savesList() {
        TypedQuery<Boolean> tq = mockTypedQuery(Boolean.class);
        when(entityManager.createQuery(SELECT, Boolean.class)).thenReturn(tq);
        when(tq.getSingleResult()).thenReturn(false);
        List<Mag> ml = mockList(Mag.class);
        MagEntity me1 = mock(MagEntity.class);
        ZonedDateTime tt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(me1.getTimeTag()).thenReturn(tt1);
        MagEntity me2 = mock(MagEntity.class);
        ZonedDateTime tt2 = ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]");
        when(me2.getTimeTag()).thenReturn(tt2);
        List<MagEntity> mel = Arrays.asList(me1, me2);
        when(magEntityMapper.map(ml)).thenReturn(mel);

        magWriterRepository.save(ml);

        assertNotNull(ml);
        verify(magEntityMapper, times(1)).map(refEq(ml));
        verify(entityManager, times(2)).createQuery(SELECT, Boolean.class);
        verify(tq, times(1)).setParameter(eq("timeTag"), refEq(tt1));
        verify(tq, times(1)).setParameter(eq("timeTag"), refEq(tt2));
        verify(tq, times(2)).getSingleResult();
        verify(entityManager, times(1)).persist(refEq(me1));
        verify(entityManager, times(1)).persist(refEq(me2));
    }

}
