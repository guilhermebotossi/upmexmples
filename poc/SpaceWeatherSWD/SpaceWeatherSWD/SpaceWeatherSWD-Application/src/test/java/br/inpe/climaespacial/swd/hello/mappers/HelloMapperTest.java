package br.inpe.climaespacial.swd.hello.mappers;

import br.inpe.climaespacial.swd.hello.dtos.Hello;
import br.inpe.climaespacial.swd.hello.entities.HelloEntity;
import br.inpe.climaespacial.swd.hello.factories.HelloFactory;
import java.time.ZonedDateTime;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultHelloMapper.class)
public class HelloMapperTest {

    @Produces
    @Mock
    private HelloFactory helloFactory;

    @Inject
    private HelloMapper helloMapper;

    @Test
    public void map_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            helloMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"helloEntity\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_returnsHello() {
        HelloEntity he = mock(HelloEntity.class);
        when(he.getModificationDate()).thenReturn(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));

        Hello h1 = new Hello();
        when(helloFactory.create()).thenReturn(h1);

        Hello h2 = helloMapper.map(he);

        assertNotNull(h2);
        assertSame(h2, h1);
        assertEquals(he.getModificationDate(), h2.getModificationDate());

        verify(helloFactory, times(1)).create();
    }
}
