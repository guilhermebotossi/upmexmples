package br.inpe.climaespacial.swd.acquisition.repositories;

import java.util.List;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;

public interface MagWriterRepository {

	void save(List<Mag> magList);

}
