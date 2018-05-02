package br.inpe.climaespacial.swd.hello.repositories;

import br.inpe.climaespacial.swd.hello.dtos.Hello;

public interface HelloRepository {

	void save();

	Hello getHello();
}
