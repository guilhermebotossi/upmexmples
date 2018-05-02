package br.inpe.climaespacial.swd.acquisition.repositories;

import java.util.List;

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;

public interface PlasmaWriterRepository {

	void save(List<Plasma> plasmaList);

}
