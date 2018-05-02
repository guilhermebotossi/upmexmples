package br.inpe.climaespacial.swd.indexes.b.repositories;

import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import br.inpe.climaespacial.swd.indexes.b.mappers.BIndexEntityMapper;
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
@AdditionalClasses({DefaultBIndexWriterRepository.class})
public class BIndexWriterRepositoryTest {

    @Mock
    @Produces
    private EntityManager entityManager;

    @Mock
    @Produces
    private BIndexEntityMapper bIndexEntityMapper;

    @Inject
    private BIndexWriterRepository bIndexWriterRepository;

    @Test
    public void save_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            bIndexWriterRepository.save(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"bIndex\" cannot be null.", re.getMessage());
    }

    @Test
    public void save_calledwithValidParameter_persists() {
        BIndex bi = mock(BIndex.class);
        BIndexEntity bie = mock(BIndexEntity.class);
        when(bIndexEntityMapper.map(bi)).thenReturn(bie);

        bIndexWriterRepository.save(bi);

        verify(bIndexEntityMapper, times(1)).map(bi);
        verify(entityManager, times(1)).persist(bie);
        verify(entityManager, times(1)).flush();
    }

}
