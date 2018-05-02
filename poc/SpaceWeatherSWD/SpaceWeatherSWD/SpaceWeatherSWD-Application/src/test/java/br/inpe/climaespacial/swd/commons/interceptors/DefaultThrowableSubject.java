
package br.inpe.climaespacial.swd.commons.interceptors;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultThrowableSubject implements ThrowableSubject {
    
    @Inject
    private ThrowableSubjectAdapter throwableSubjectAdapter;
    
    @TransientFaultInterceptor
    public String throwsWhenCalled() {        
        return throwableSubjectAdapter.foo();
    }

}
