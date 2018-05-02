package br.inpe.climaespacial.swd.kp.forecast.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
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

import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPValueReaderRepository.class)
public class KPValueReaderRepositoryTest {
	
    String jpql = "SELECT kpve FROM CIndexEntity cie, KPValueEntity kpve WHERE " 
            + "cie.timeTag = kpve.timeTag AND "
            + "cie.timeTag NOT IN (SELECT kpfe.indexesTimeTag FROM KPForecastEntity kpfe) "
            + "AND cie.timeTag < (SELECT MAX(kpve.timeTag) FROM KPValueEntity kpve) ORDER BY cie.timeTag ASC";

    @Mock
    @Produces
    private EntityManager entityManager;

    @Mock
    @Produces
    private KPValueMapper kpValueMapper;

    @Inject
    private KPValueReaderRepository kpValueReaderRepository;

    @Test
    public void getLastKPValue_called_returnsNull() { 

        TypedQuery<KPValueEntity> tq = mockTypedQuery(KPValueEntity.class);
        when(entityManager.createQuery(jpql, KPValueEntity.class)).thenReturn(tq);

        List<KPValueEntity> kpvel = mockList(KPValueEntity.class);
        when(tq.setMaxResults(1)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(kpvel);

        when(kpvel.isEmpty()).thenReturn(true);
        when(kpValueMapper.map((KPValueEntity) null)).thenReturn(null); 

        KPValue kpv2 = kpValueReaderRepository.getLastKPValue();

        assertNull(kpv2);
        verify(entityManager).createQuery(jpql, KPValueEntity.class);
        verify(tq).setMaxResults(1);
        verify(tq).getResultList();
        verify(kpvel).isEmpty();
        verify(kpValueMapper).map((KPValueEntity) null); 
    }

    @Test 
    public void getLastKPValue_called_returnsKPValueEntity() {
        TypedQuery<KPValueEntity> tq = mockTypedQuery(KPValueEntity.class);
        when(entityManager.createQuery(jpql, KPValueEntity.class)).thenReturn(tq);

        List<KPValueEntity> kpvel = mockList(KPValueEntity.class);
        when(tq.setMaxResults(1)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(kpvel);

        when(kpvel.isEmpty()).thenReturn(false);
        KPValueEntity kpve1 = mock(KPValueEntity.class);
        KPValue kpv1 = mock(KPValue.class);
        when(kpvel.get(0)).thenReturn(kpve1);

        when(kpValueMapper.map(kpve1)).thenReturn(kpv1);

        KPValue kpv2 = kpValueReaderRepository.getLastKPValue();

        assertNotNull(kpv2);
        assertSame(kpv1, kpv2);
        verify(entityManager).createQuery(jpql, KPValueEntity.class);
        verify(tq).setMaxResults(1); 
        verify(tq).getResultList();
        verify(kpvel).isEmpty();
        verify(kpvel).get(0);
        verify(kpValueMapper).map(kpve1);
    }

}
