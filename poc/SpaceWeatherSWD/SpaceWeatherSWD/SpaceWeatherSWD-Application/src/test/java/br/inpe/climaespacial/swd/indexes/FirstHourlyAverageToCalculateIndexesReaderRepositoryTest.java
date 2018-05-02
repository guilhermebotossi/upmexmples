
package br.inpe.climaespacial.swd.indexes;

import br.inpe.climaespacial.swd.commons.EmbraceMockito;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultFirstHourlyAverageToCalculateIndexesReaderRepository.class)
public class FirstHourlyAverageToCalculateIndexesReaderRepositoryTest {
    
    private static final String QUERY = "SELECT hae.timeTag FROM HourlyAverageEntity hae ORDER BY hae.timeTag ASC";

    @Produces
    @Mock
    private EntityManager entityManager;

    @Inject
    private FirstHourlyAverageToCalculateIndexesReaderRepository firstHourlyAverageToCalculateIndexesReaderRepository;

    @Test
    public void getFirstHour_called_returnsNull() {

        TypedQuery<ZonedDateTime> tq = EmbraceMockito.mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(QUERY, ZonedDateTime.class)).thenReturn(tq);
        
        ZonedDateTime zdt = null;        
        List<ZonedDateTime> zdtl = mockList(ZonedDateTime.class);
        when(zdtl.get(0)).thenReturn(zdt);
        when(tq.getResultList()).thenReturn(zdtl);

        ZonedDateTime fh = firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour();

        assertNull(fh);
        assertSame(zdt, fh);
        verify(entityManager).createQuery(QUERY, ZonedDateTime.class);
        verify(tq).getResultList();
        verify(zdtl).get(0);
    }

    @Test
    public void getFirstHour_called_returnsFirstTimeTag() {
        
        TypedQuery<ZonedDateTime> tq = EmbraceMockito.mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(QUERY, ZonedDateTime.class)).thenReturn(tq);
        
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");        
        List<ZonedDateTime> zdtl = mockList(ZonedDateTime.class);
        when(zdtl.get(0)).thenReturn(zdt);
        when(tq.getResultList()).thenReturn(zdtl);        

        ZonedDateTime fh = firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour();
        assertNotNull(fh);
        assertSame(zdt, fh);
        verify(entityManager).createQuery(QUERY, ZonedDateTime.class);
        verify(tq).getResultList();
        verify(zdtl).get(0);
    }

}
