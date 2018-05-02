package br.inpe.climaespacial.swd.indexes.v.repositories;

import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import br.inpe.climaespacial.swd.indexes.v.mappers.VIndexEntityMapper;
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
@AdditionalClasses({DefaultVIndexWriterRepository.class})
public class VIndexWriterRepositoryTest {

    @Mock
    @Produces
    private EntityManager entityManager;

    @Mock
    @Produces
    private VIndexEntityMapper vIndexEntityMapper;

    @Inject
    private VIndexWriterRepository vIndexWriterRepository;

    @Test
    public void save_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            vIndexWriterRepository.save(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"vIndex\" cannot be null.", re.getMessage());
    }

    @Test
    public void save_calledwithValidParameter_persists() {
        VIndex vi = mock(VIndex.class);
        VIndexEntity vie = mock(VIndexEntity.class);
        when(vIndexEntityMapper.map(vi)).thenReturn(vie);

        vIndexWriterRepository.save(vi);

        verify(vIndexEntityMapper, times(1)).map(vi);
        verify(entityManager, times(1)).persist(vie);
        verify(entityManager, times(1)).flush();
    }

}
