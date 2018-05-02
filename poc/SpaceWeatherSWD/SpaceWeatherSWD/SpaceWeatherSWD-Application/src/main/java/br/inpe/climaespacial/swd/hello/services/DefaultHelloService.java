package br.inpe.climaespacial.swd.hello.services;

import br.inpe.climaespacial.swd.hello.dtos.Hello;
import br.inpe.climaespacial.swd.hello.repositories.HelloRepository;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Dependent
public class DefaultHelloService implements HelloService {

    @Inject
    private HelloRepository helloRepository;

    @Override
    @Transactional
    public Hello hello() {
        helloRepository.save();
        return helloRepository.getHello();
    }

}
