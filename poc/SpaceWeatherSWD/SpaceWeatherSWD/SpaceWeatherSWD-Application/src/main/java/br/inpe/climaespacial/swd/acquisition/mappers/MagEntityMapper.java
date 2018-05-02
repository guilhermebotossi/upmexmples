package br.inpe.climaespacial.swd.acquisition.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;

public interface MagEntityMapper {

	List<MagEntity> map(List<Mag> magList);

}
