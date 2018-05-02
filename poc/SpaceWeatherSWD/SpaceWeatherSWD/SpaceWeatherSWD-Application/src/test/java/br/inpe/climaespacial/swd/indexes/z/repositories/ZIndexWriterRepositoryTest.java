package br.inpe.climaespacial.swd.indexes.z.repositories;

import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import br.inpe.climaespacial.swd.indexes.z.mappers.ZIndexEntityMapper;
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
@AdditionalClasses({DefaultZIndexWriterRepository.class})
public class ZIndexWriterRepositoryTest {

    @Mock
    @Produces
    private EntityManager entityManager;

    @Mock
    @Produces
    private ZIndexEntityMapper zIndexEntityMapper;

    @Inject
    private ZIndexWriterRepository zIndexWriterRepository;

    @Test
    public void save_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            zIndexWriterRepository.save(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"zIndex\" cannot be null.", re.getMessage());
    }

    @Test
    public void save_calledwithValidParameter_persists() {
        ZIndex zi = mock(ZIndex.class);
        ZIndexEntity zie = mock(ZIndexEntity.class);
        when(zIndexEntityMapper.map(zi)).thenReturn(zie);

        zIndexWriterRepository.save(zi);

        verify(zIndexEntityMapper, times(1)).map(zi);
        verify(entityManager, times(1)).persist(zie);
        verify(entityManager, times(1)).flush();
    }

}
