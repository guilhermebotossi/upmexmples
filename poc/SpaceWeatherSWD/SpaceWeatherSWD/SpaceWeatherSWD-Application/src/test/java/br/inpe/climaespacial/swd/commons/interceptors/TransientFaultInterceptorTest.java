package br.inpe.climaespacial.swd.commons.interceptors;

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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    DefaultThrowableSubject.class,
    DefaultTransientFaultInterceptor.class
})
public class TransientFaultInterceptorTest {

    private static final String FOO_A = "foo_a";
    private static final String FOO_B = "foo_b";

    @Produces
    @Mock
    private ThrowableSubjectAdapter throwableSubjectAdapter;

    @Inject
    private ThrowableSubject throwableSubject;

    @Test
    public void throwsWhenCalled_called_reattempts() {

        when(throwableSubjectAdapter.foo()).thenThrow(RuntimeException.class).thenReturn(FOO_A);

        String r = throwableSubject.throwsWhenCalled();

        verify(throwableSubjectAdapter, times(2)).foo();
        assertNotNull(r);
        assertEquals(FOO_A, r);
    }

    @Test
    public void throwsWhenCalled_calledRepeatedly_reattempts() {

        when(throwableSubjectAdapter.foo())
                .thenThrow(RuntimeException.class)
                .thenReturn(FOO_A)
                .thenThrow(RuntimeException.class)
                .thenReturn(FOO_B);

        String r1 = throwableSubject.throwsWhenCalled();
        String r2 = throwableSubject.throwsWhenCalled();

        verify(throwableSubjectAdapter, times(4)).foo();
        assertNotNull(r1);
        assertEquals(FOO_A, r1);
        assertNotNull(r2);
        assertEquals(FOO_B, r2);
    }

    @Test
    public void throwsWhenCalled_called_reattemptsAndFails() {
        RuntimeException re = new RuntimeException();
        RuntimeException r = new RuntimeException();

        when(throwableSubjectAdapter.foo()).thenThrow(re).thenThrow(re);

        try {
            throwableSubject.throwsWhenCalled();
        } catch (RuntimeException ex) {
            r = ex;
        }

        verify(throwableSubjectAdapter, times(2)).foo();
        assertSame(r, re);
    }

}
