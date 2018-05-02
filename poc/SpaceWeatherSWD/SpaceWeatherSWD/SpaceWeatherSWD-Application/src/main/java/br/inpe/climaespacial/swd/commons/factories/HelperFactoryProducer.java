package br.inpe.climaespacial.swd.commons.factories;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Dependent
public class HelperFactoryProducer {

    @Produces
    public <T> HelperFactory<T> create(InjectionPoint injectionPoint) {
        try {
            Field f = (Field) injectionPoint.getMember();
            Type t = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
            String tn = t.getTypeName();
            @SuppressWarnings("unchecked")
            Class<T> c = (Class<T>) Class.forName(tn);
            return new DefaultHelperFactory<>(c);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

}
