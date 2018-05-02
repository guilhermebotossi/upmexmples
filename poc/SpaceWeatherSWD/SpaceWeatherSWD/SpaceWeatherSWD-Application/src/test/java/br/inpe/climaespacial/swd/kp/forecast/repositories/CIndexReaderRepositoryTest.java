package br.inpe.climaespacial.swd.kp.forecast.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
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

import br.inpe.climaespacial.swd.commons.utils.CollectionUtils;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.mappers.CIndexMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultCIndexReaderRepository.class)
public class CIndexReaderRepositoryTest {

    @Mock
    @Produces
    private EntityManager entityManager;

    @Mock
    @Produces
    private CIndexMapper cIndexMapper;
    
    @Mock
    @Produces
    private CollectionUtils collectionUtils; 

    @Inject
    private CIndexReaderRepository cIndexReaderRepository;
    
    @Test
    public void getLastCIndexes_called_succeeds() {
        int maxResults = 9;
        String jpql = "SELECT cie FROM CIndexEntity cie WHERE cie.timeTag <= :timeTag ORDER BY cie.timeTag DESC";
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]");
        TypedQuery<CIndexEntity> tq = mockTypedQuery(CIndexEntity.class);
        when(entityManager.createQuery(jpql, CIndexEntity.class)).thenReturn(tq);
        when(tq.setParameter("timeTag", zdt)).thenReturn(tq);
        when(tq.setMaxResults(maxResults)).thenReturn(tq);
        List<CIndexEntity> ciel = mockList(CIndexEntity.class); 
        when(tq.getResultList()).thenReturn(ciel);
        List<CIndex> cil1 = mockList(CIndex.class);
        when(cIndexMapper.map(ciel)).thenReturn(cil1);
        when(cil1.size()).thenReturn(maxResults);
        
        List<CIndex> cil2 = cIndexReaderRepository.getLastCIndexes(zdt); 
        
        assertNotNull(cil2);
        assertThat(cil2, is(not(empty())));
        assertThat(cil2, hasSize(maxResults));
        
        verify(entityManager).createQuery(jpql, CIndexEntity.class);
        verify(tq).setParameter("timeTag", zdt);
        verify(tq).setMaxResults(maxResults);
        verify(tq).getResultList(); 
        verify(collectionUtils).reverse(ciel);
        verify(cIndexMapper).map(ciel);
    }
    
}
