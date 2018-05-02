package br.inpe.climaespacial.swd.hello.services;

import br.inpe.climaespacial.swd.hello.dtos.Hello;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.hello.repositories.HelloRepository;
import static org.mockito.Mockito.mock;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultHelloService.class})
public class HelloServiceTest {

    @Produces
    @Mock
    private HelloRepository helloRepository;

    @Inject
    private HelloService helloService;

    @Test
    public void hello_called_returnsHello() {
        Hello h1 = mock(Hello.class);
        when(helloRepository.getHello()).thenReturn(h1);

        Hello h2 = helloService.hello();

        assertSame(h2, h1);
        verify(helloRepository, times(1)).save();
        verify(helloRepository, times(1)).getHello();

    }

}
