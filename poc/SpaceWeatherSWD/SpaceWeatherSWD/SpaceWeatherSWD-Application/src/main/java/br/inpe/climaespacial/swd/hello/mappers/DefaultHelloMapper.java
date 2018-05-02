package br.inpe.climaespacial.swd.hello.mappers;

import br.inpe.climaespacial.swd.hello.dtos.Hello;
import br.inpe.climaespacial.swd.hello.entities.HelloEntity;
import br.inpe.climaespacial.swd.hello.factories.HelloFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultHelloMapper implements HelloMapper {

    @Inject
    private HelloFactory helloFactory;

    @Override
    public Hello map(HelloEntity helloEntity) {
        throwIfNull(helloEntity, "helloEntity");

        Hello h = helloFactory.create();
        h.setModificationDate(helloEntity.getModificationDate());
        return h;
    }

}
