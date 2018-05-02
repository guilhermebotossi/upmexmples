package br.inpe.climaespacial.swd.acquisition.repositories;

import br.inpe.climaespacial.swd.acquisition.qualifiers.PlasmaLastRecordQualifier;
import java.time.ZonedDateTime;
import java.util.Arrays;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultPlasmaLastRecordRepository.class)
public class PlasmaLastRecordRepositoryTest {

    @Produces
    @Mock
    private EntityManager entityManager;

    @Inject
    @PlasmaLastRecordQualifier
    private LastRecordRepository lastRecordRepository;

    private static final String SELECT_SQL = "SELECT MAX(pe.timeTag) FROM PlasmaEntity pe";
    
    @Test
    public void getLast_called_returnsNull() {

        Query query = (Query) mock(Query.class);       
        when(entityManager.createQuery(SELECT_SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList());

        ZonedDateTime actualLastRecordDateTime = lastRecordRepository.getLast();

        verify(entityManager, times(1)).createQuery(SELECT_SQL);
        verify(query, times(1)).setMaxResults(1);
        verify(query, times(1)).getResultList();
        assertNull(actualLastRecordDateTime);
    }

    @Test
    public void getLast_called_returnsZonedDateTime() {

        ZonedDateTime expectedLastRecordDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Query query = (Query) mock(Query.class);
        when(entityManager.createQuery(SELECT_SQL)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(expectedLastRecordDateTime));

        ZonedDateTime actualLastRecordDateTime = lastRecordRepository.getLast();

        verify(entityManager, times(1)).createQuery(SELECT_SQL);
        verify(query, times(1)).setMaxResults(1);
        verify(query, times(1)).getResultList();
        assertNotNull(actualLastRecordDateTime);
        assertSame(expectedLastRecordDateTime, actualLastRecordDateTime);
    }

}
