package br.inpe.climaespacial.swd.acquisition.repositories;

import java.util.List;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;

public interface MagReaderRepository {

	List<Mag> listLast();

}
