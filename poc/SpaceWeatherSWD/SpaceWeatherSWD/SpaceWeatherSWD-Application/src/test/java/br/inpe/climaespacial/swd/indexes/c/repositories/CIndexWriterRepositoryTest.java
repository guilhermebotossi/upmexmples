package br.inpe.climaespacial.swd.indexes.c.repositories;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.mappers.CIndexEntityMapper;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultCIndexWriterRepository.class})
public class CIndexWriterRepositoryTest {

    @Mock
    @Produces
    private EntityManager entityManager;

    @Mock
    @Produces
    private CIndexEntityMapper cIndexEntityMapper;

    @Inject
    private CIndexWriterRepository cIndexWriterRepository;

    @Test
    public void save_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            cIndexWriterRepository.save(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"cIndex\" cannot be null.", re.getMessage());
    }

    @Test
    public void save_calledwithValidParameter_persists() {
        CIndex ci = mock(CIndex.class);
        CIndexEntity cie = mock(CIndexEntity.class);
        when(cIndexEntityMapper.map(ci)).thenReturn(cie);

        cIndexWriterRepository.save(ci);

        verify(cIndexEntityMapper, times(1)).map(ci);
        verify(entityManager, times(1)).persist(cie);
        verify(entityManager, times(1)).flush();
    }

}
