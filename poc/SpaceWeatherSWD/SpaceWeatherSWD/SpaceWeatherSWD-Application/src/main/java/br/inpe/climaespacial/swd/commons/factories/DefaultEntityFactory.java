package br.inpe.climaespacial.swd.commons.factories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.entities.BaseEntity;
import br.inpe.climaespacial.swd.commons.providers.UUIDProvider;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public abstract class DefaultEntityFactory<TEntity extends BaseEntity> implements EntityFactory<TEntity> {

    @Inject
    private UUIDProvider uuidProvider;

    @Inject
    private DateTimeProvider dateTimeProvider;

    @Override
    public TEntity create() {
        TEntity entity = doCreate();
        entity.setId(uuidProvider.getUUID());
        entity.setCreationDate(dateTimeProvider.getNow());
        entity.setModificationDate(dateTimeProvider.getNow());
        return entity;
    }

    protected abstract TEntity doCreate();
}
