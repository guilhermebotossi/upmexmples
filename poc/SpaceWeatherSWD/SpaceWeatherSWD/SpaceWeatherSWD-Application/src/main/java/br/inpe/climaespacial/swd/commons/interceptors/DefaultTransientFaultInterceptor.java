package br.inpe.climaespacial.swd.commons.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@TransientFaultInterceptor
public class DefaultTransientFaultInterceptor {

    private static final int TRIES_LIMIT = 1;

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        int t = 0;
        while (true) {
            try {
                return ctx.proceed();
            } catch (Throwable ex) {
                if (t++ == TRIES_LIMIT) {
                    throw ex;
                }
            }
        }
    }

}
