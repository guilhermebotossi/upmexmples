package br.inpe.climaespacial.swd.calculation.repositories;

import br.inpe.climaespacial.swd.calculation.dtos.CalculatedValues;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.calculation.mappers.CalculatedValuesEntityMapper;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import java.util.Arrays;
import java.util.List;
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
@AdditionalClasses(DefaultCalculatedValuesWriterRepository.class)
public class CalculatedValuesWriterRepositoryTest {

    @Produces
    @Mock
    private CalculatedValuesEntityMapper calculatedValuesEntityMapper;

    @Produces
    @Mock
    private EntityManager entityManager;

    @Inject
    private CalculatedValuesWriterRepository calculatedValuesWriterRepository;

    @Test
    public void save_called_WithPlasmaListNull_throws() {
        List<CalculatedValues> cvl = null;
        RuntimeException re = null;

        try {
            calculatedValuesWriterRepository.save(cvl);
        } catch (RuntimeException ex) {
            re = ex;
        }

        assertNotNull(re);
        assertEquals("Argument \"calculatedValuesList\" cannot be null.", re.getMessage());
    }

    @Test
    public void save_called_savesList() {
        CalculatedValuesEntity cv1 = mock(CalculatedValuesEntity.class);
        CalculatedValuesEntity cv2 = mock(CalculatedValuesEntity.class);
        List<CalculatedValuesEntity> cvel = Arrays.asList(cv1, cv2);
        List<CalculatedValues> cvl = mockList(CalculatedValues.class);
        when(calculatedValuesEntityMapper.map(cvl)).thenReturn(cvel);

        calculatedValuesWriterRepository.save(cvl);

        assertNotNull(cvl);
        verify(calculatedValuesEntityMapper, times(1)).map(cvl);
        verify(entityManager, times(1)).persist(cv1);
        verify(entityManager, times(1)).persist(cv2);
        verify(entityManager, times(1)).flush();
        verify(entityManager, times(1)).clear();
    }
}
