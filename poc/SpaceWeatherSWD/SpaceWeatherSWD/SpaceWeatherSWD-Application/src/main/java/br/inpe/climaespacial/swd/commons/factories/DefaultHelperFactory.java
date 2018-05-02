package br.inpe.climaespacial.swd.commons.factories;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

public final class DefaultHelperFactory<T> implements HelperFactory<T> {

    private BeanManager beanManager;

    private Class<T> type;

    public DefaultHelperFactory() {
        beanManager = CDI.current().getBeanManager();
    }

    public DefaultHelperFactory(Class<T> type) {
        this();
        this.type = type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T create() {
        Bean<T> bean = (Bean<T>) beanManager.resolve(beanManager.getBeans(type));
        return (T) beanManager.getReference(bean, bean.getBeanClass(), beanManager.createCreationalContext(bean));
    }
}
