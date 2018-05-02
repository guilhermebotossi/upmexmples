package br.inpe.climaespacial.swd.acquisition.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;

public interface MagMapper {

	List<Mag> map(String content);

}
