package br.inpe.climaespacial.swd.values.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultValuesReaderRepository.class)
public class ValuesReaderRepositoryTest {
    
    private static final String SQL_MAG_ENTITY_SELECT = "SELECT MAX(me.timeTag) FROM MagEntity me "
            + "WHERE me.bxGsm IS NOT NULL OR "
            + "me.byGsm IS NOT NULL OR "
            + "me.bzGsm IS NOT NULL OR "
            + "me.latGsm IS NOT NULL OR "
            + "me.lonGsm IS NOT NULL OR "
            + "me.bt IS NOT NULL";
    
    private static final String SQL_PLASMA_ENTITY_SELECT = "SELECT MAX(pe.timeTag) FROM PlasmaEntity pe "
            + "WHERE pe.density IS NOT NULL OR "
            + "pe.speed IS NOT NULL OR "
            + "pe.temperature IS NOT NULL";
    
    private static final String SQL_CALCULATED_VALUES_ENTITY_SELECT = "SELECT MAX(cve.timeTag) FROM CalculatedValuesEntity cve "
            + "WHERE cve.ey IS NOT NULL OR "
            + "cve.dpr IS NOT NULL OR "
            + "cve.rmp IS NOT NULL";
    
    private ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");

    @Mock
    @Produces
    private EntityManager entityManager;
    
    @Inject
    private ValuesReaderRepository valuesReaderRepository;

    @Test
    public void lastValuesDate_called_returnNull(){
        createTQ1(null);
        createTQ2(null);
        createTQ3(null);
        
        ZonedDateTime zdt = valuesReaderRepository.lastValuesDate(); 
        
        assertNull(zdt); 
        verifyEntityManager(); 
    }
    
    @Test
    public void lastValuesDate_calledWithNull_returnDateTime(){
        createTQ1(finalDateTime);
        createTQ2(null);
        createTQ3(null);
        
        ZonedDateTime zdt = valuesReaderRepository.lastValuesDate(); 
        
        assertNotNull(zdt); 
        assertSame(finalDateTime, zdt);
        verifyEntityManager(); 
    }
    
    @Test
    public void lastValuesDate_called_returnMaxDateTime(){
        createTQ1(finalDateTime);
        createTQ2(ZonedDateTime.parse("2017-01-03T11:00:00z[UTC]"));
        createTQ3(ZonedDateTime.parse("2017-01-03T10:00:00z[UTC]"));
        
        ZonedDateTime zdt = valuesReaderRepository.lastValuesDate(); 
        
        assertNotNull(zdt); 
        assertSame(finalDateTime, zdt);
        verifyEntityManager(); 
    }
    
    private void createTQ1(ZonedDateTime zonedDateTime) {
        TypedQuery<ZonedDateTime> tq1 = mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(SQL_MAG_ENTITY_SELECT, ZonedDateTime.class)).thenReturn(tq1);
        when(tq1.getSingleResult()).thenReturn(zonedDateTime);
    }
    
    
    private void createTQ2(ZonedDateTime zonedDateTime) {
        TypedQuery<ZonedDateTime> tq2 = mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(SQL_PLASMA_ENTITY_SELECT, ZonedDateTime.class)).thenReturn(tq2);
        when(tq2.getSingleResult()).thenReturn(zonedDateTime);
    }

    private void createTQ3(ZonedDateTime zonedDateTime) {
        TypedQuery<ZonedDateTime> tq3 = mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(SQL_CALCULATED_VALUES_ENTITY_SELECT, ZonedDateTime.class)).thenReturn(tq3);
        when(tq3.getSingleResult()).thenReturn(zonedDateTime);
    }
    
    private void verifyEntityManager() {
        verify(entityManager).createQuery(SQL_MAG_ENTITY_SELECT, ZonedDateTime.class);
        verify(entityManager).createQuery(SQL_PLASMA_ENTITY_SELECT, ZonedDateTime.class);      
        verify(entityManager).createQuery(SQL_CALCULATED_VALUES_ENTITY_SELECT, ZonedDateTime.class);
    }
}
