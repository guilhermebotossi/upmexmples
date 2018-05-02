package br.inpe.climaespacial.swd.acquisition.repositories;

import br.inpe.climaespacial.swd.acquisition.qualifiers.MagLastRecordQualifier;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultMagLastRecordRepository.class)
public class MagLastRecordRepositoryTest {

    private static final String SELECT_SQL = "SELECT MAX(m.timeTag) FROM MagEntity m";

    @Produces
    @Mock
    private EntityManager entityManager;

    @Inject
    @MagLastRecordQualifier
    private LastRecordRepository lastRecordRepository;

    @Test
    public void getLast_called_succeedsWithNullResponse() {
        Query q = mock(Query.class);
        when(entityManager.createQuery(SELECT_SQL)).thenReturn(q);
        when(q.setMaxResults(1)).thenReturn(q);
        when(q.getResultList()).thenReturn(Collections.EMPTY_LIST);

        ZonedDateTime last = lastRecordRepository.getLast();

        assertNull(last);
        verify(entityManager, times(1)).createQuery(SELECT_SQL);
        verify(q, times(1)).setMaxResults(1);
        verify(q, times(1)).getResultList();

    }

    @Test
    public void getLast_called_succeeds() {
        Query q = mock(Query.class);
        when(entityManager.createQuery(SELECT_SQL)).thenReturn(q);
        when(q.setMaxResults(1)).thenReturn(q);
        ZonedDateTime lr = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(q.getResultList()).thenReturn(Arrays.asList(lr));

        ZonedDateTime last = lastRecordRepository.getLast();

        assertNotNull(last);
        assertSame(lr, last);
        verify(entityManager, times(1)).createQuery(SELECT_SQL);
        verify(q, times(1)).setMaxResults(1);
        verify(q, times(1)).getResultList();
    }

}
