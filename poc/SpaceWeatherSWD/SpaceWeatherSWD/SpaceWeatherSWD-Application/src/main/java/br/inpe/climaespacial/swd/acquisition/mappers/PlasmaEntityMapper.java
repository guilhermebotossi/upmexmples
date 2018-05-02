package br.inpe.climaespacial.swd.acquisition.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;

public interface PlasmaEntityMapper {

	List<PlasmaEntity> map(List<Plasma> plasmaList);

}
