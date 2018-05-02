package br.inpe.climaespacial.swd.kp.values.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;
import br.inpe.climaespacial.swd.kp.forecast.repositories.KPValueMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPValueReaderRepository.class)
public class KPValueReaderRepositoryTest {

    @Mock
    @Produces 
    private EntityManager entityManager;

    @Mock
    @Produces
    private KPValueMapper kpValueMapper;
    
    @Mock
    @Produces
    private CollectionUtils collectionUtils;

    @Inject
    private KPValueReaderRepository kpValueReaderRepository; 
    

    @Test
    public void getLastKPIndexes_called_returns() {
        String sql = "SELECT kpve FROM KPValueEntity kpve WHERE kpve. timeTag < (SELECT MAX(kpve.timeTag) FROM KPValueEntity kpve) ORDER BY kpve.timeTag DESC";
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-01T06:00:00z[UTC]");
        ZonedDateTime zdt4 = ZonedDateTime.parse("2017-01-01T09:00:00z[UTC]"); 

        TypedQuery<KPValueEntity> tq = mockTypedQuery(KPValueEntity.class);
        when(entityManager.createQuery(sql, KPValueEntity.class)).thenReturn(tq);
        when(tq.setMaxResults(4)).thenReturn(tq);

        List<KPValueEntity> kpvel = mockList(KPValueEntity.class);
        when(tq.getResultList()).thenReturn(kpvel);

        List<KPValue> kpl1 = new ArrayList<>();
        kpl1.add(createKP(zdt1));
        kpl1.add(createKP(zdt2));
        kpl1.add(createKP(zdt3));
        kpl1.add(createKP(zdt4));
        when(kpValueMapper.map(kpvel)).thenReturn(kpl1);

        List<KPValue> kpvl2 = kpValueReaderRepository.getLastKPIndexes(); 

        assertNotNull(kpvl2);
        assertSame(kpl1, kpvl2);
        Collections.reverse(kpl1);
        assertThat(kpvl2, is(not(empty())));
        assertThat(kpvl2, hasSize(4));
        assertSame(zdt4, kpvl2.get(0).getTimeTag());
        assertSame(zdt3, kpvl2.get(1).getTimeTag());
        assertSame(zdt2, kpvl2.get(2).getTimeTag());
        assertSame(zdt1, kpvl2.get(3).getTimeTag());
        verify(entityManager).createQuery(sql, KPValueEntity.class);
        verify(tq).setMaxResults(4);
        verify(tq).getResultList();
        verify(kpValueMapper).map(kpvel);
        verify(collectionUtils).reverse(kpl1);
    }
    
    private KPValue createKP(ZonedDateTime timeTag) {
        KPValue kpv = new KPValue();
        kpv.setTimeTag(timeTag);
        kpv.setKPValue(1L);
        kpv.setKPValueFlag(KPValueFlag.ZERO);

        return kpv;
    }
    
    

}
