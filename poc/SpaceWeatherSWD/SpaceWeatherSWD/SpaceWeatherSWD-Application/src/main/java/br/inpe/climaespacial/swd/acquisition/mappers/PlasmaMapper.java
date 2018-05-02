package br.inpe.climaespacial.swd.acquisition.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;

public interface PlasmaMapper {

	List<Plasma> map(String content);

}
