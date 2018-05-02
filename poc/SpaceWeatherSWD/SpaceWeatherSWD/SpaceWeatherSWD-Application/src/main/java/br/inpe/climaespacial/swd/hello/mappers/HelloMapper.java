package br.inpe.climaespacial.swd.hello.mappers;

import br.inpe.climaespacial.swd.hello.dtos.Hello;
import br.inpe.climaespacial.swd.hello.entities.HelloEntity;

public interface HelloMapper {
    
    Hello map(HelloEntity helloEntity); 
    
}
